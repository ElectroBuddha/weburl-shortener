package com.example.weburlshortener.controller.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.repository.UrlRepository;
import com.example.weburlshortener.service.AccountService;
import com.example.weburlshortener.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD) // Flush database before each test
public class UrlControllerTests {
	
	@Autowired
	protected AccountService accountService;
	
	@Autowired
	protected UrlRepository urlRepository;

	@LocalServerPort
	protected int port;
	
	protected TestRestTemplate restTemplate = new TestRestTemplate();
 
	protected HttpHeaders headers = new HttpHeaders();
    
	protected String username = "user1";
	protected String password = "abc123";
	protected Account account;
	
	protected String urlAddress  = "http://www.google.com";
	protected int redirectType   = 301;
    protected String urlShortKey = "a1b2c3";
	
    @Before
    public void setup() throws Exception  {
    	headers.set("Content-Type", "application/json");
    	headers.set("Accept", "application/json");
    	
    	// Create test user directly via service
		this.account = this.accountService.createAccount(this.username, this.password);
		
		String credentials = String.format("%s:%s", this.username, this.password);
		
		// Set authorization header
		headers.set("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(credentials.getBytes())));
    }
    
    @Test
    public void testAuthorizationFailsOnBadCredentials() throws Exception {
    	// Override HttpRequestFactory so that we can get proper failure when accessing rest api with wrong credentials for basic authentication
    	restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    	restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler() {
    	    public boolean hasError(ClientHttpResponse response) throws IOException {
    	        HttpStatus statusCode = response.getStatusCode();
    	        return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
    	    }
    	});
    	
    	headers.set("Authorization", "Basic +++++badcredentials+++++");
    	
    	ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/register"), HttpMethod.POST, this.createHttpEntity(null, this.headers), String.class);
        
        assertEquals("401 UNAUTHORIZED", response.getStatusCode().toString());
        assertTrue(response.getBody().contains("Unauthorized"));
    }
    
    @Test
    public void testCanRegisterUrl() throws Exception {
    	HashMap<String, Object> body = new HashMap<>();
    	body.put("url", "http://www.google.com");
    	body.put("redirectType", 301);
    	
        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/register"), HttpMethod.POST, this.createHttpEntity(body, this.headers), String.class);
        
        assertEquals("201 CREATED", response.getStatusCode().toString());
        assertTrue(response.getBody().contains("shortUrl"));
    }
    
    @Test
    public void testCanFetchRegularUserStatistics() throws Exception {
    	// Create Url directly via repo
    	Url url = new Url(this.account.getId(), this.urlAddress, this.redirectType, this.urlShortKey, null);
    	url.setNumOfHits(3);
		url = this.urlRepository.save(url);
    	
		// Fetch statistics via rest api
        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/statistic"), HttpMethod.GET, this.createHttpEntity(null, this.headers), String.class);
        
        assertEquals("200 OK", response.getStatusCode().toString());
        assertTrue(response.getBody().contains(url.getAddress()));
        assertTrue(response.getBody().contains(String.valueOf(url.getNumOfHits())));
    }
    
    @Test
    public void testCanFetchAdminUserCumulativeStatistics() throws Exception {
    	// Create Url for regular user (directly via repo)
    	Url url1 = new Url(this.account.getId(), this.urlAddress, this.redirectType, this.urlShortKey, null);
    	url1.setNumOfHits(3);
		url1 = this.urlRepository.save(url1);
		
		// Create Url with the same address for admin user (directly via repo)
    	Url url2 = new Url(1, this.urlAddress, this.redirectType, Utils.getRandomStringForUrlShortKey(), null);
    	url2.setNumOfHits(2);
		url2 = this.urlRepository.save(url2);
    	
		// Authenticate as admin:admin
    	// (the admin user is always created via data.sql import script)
    	String credentials = String.format("%s:%s", "admin", "admin");
		headers.set("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(credentials.getBytes())));
		
		// Fetch statistics via rest api
        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/statistic"), HttpMethod.GET, this.createHttpEntity(null, this.headers), String.class);
        
        assertEquals("200 OK", response.getStatusCode().toString());
        assertTrue(response.getBody().contains(this.urlAddress));
        
        // Assert that num of hits is equal to sum of hits from both urls
        assertTrue(response.getBody().contains(String.valueOf(url1.getNumOfHits() + url2.getNumOfHits())));
    }
    
    @Test
    public void testCanFetchPerUserStatistics() throws Exception {
    	// Create Url for regular user (directly via repo)
    	Url url = new Url(this.account.getId(), this.urlAddress, this.redirectType, this.urlShortKey, null);
    	url.setNumOfHits(3);
		url = this.urlRepository.save(url);
		
    	// Authenticate as admin:admin
    	// (the admin user is always created via data.sql import script)
    	String credentials = String.format("%s:%s", "admin", "admin");
		headers.set("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(credentials.getBytes())));
		
		// Fetch statistics via rest api
        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort(String.format("/statistic/%s", this.account.getUsername())), HttpMethod.GET, this.createHttpEntity(null, this.headers), String.class);
        
        assertEquals("200 OK", response.getStatusCode().toString());
        assertTrue(response.getBody().contains(url.getAddress()));
        assertTrue(response.getBody().contains(String.valueOf(url.getNumOfHits())));
        
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    
    private HttpEntity<HashMap<String, Object>> createHttpEntity(HashMap<String, Object> body, HttpHeaders headers) {
    	HttpEntity<HashMap<String, Object>> entity = 
        		new HttpEntity<HashMap<String, Object>>(body, this.headers);
    	
    	return entity;
    }
}
