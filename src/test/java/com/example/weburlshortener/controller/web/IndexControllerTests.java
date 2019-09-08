package com.example.weburlshortener.controller.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.service.UrlService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTests {
	
	@Autowired
	protected UrlService urlService;

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    protected String urlAddress  = "http://www.google.com";
	protected int redirectType   = 301;

    @Test
    public void testShouldReturnHomePage() throws Exception {

    	String response = 
    			this.restTemplate.getForObject(this.createURLWithPort("/"), String.class);
    	
    	assertTrue(response.contains("Home Page"));
    }
    
    @Test
    public void testShouldReturnHelpPage() throws Exception {

    	String response = 
    			this.restTemplate.getForObject(this.createURLWithPort("/help"), String.class);
    	
    	assertTrue(response.contains("Help Page"));
    }
    
    @Test
    public void testShouldRedirectShortUrlToOriginalUrl() throws Exception {

    	// Create Url for admin user (directly via repo)
    	// (the admin user is always created via data.sql import script)
    	Url url = this.urlService.createUrl(1, this.urlAddress, this.redirectType);
    	url.setBaseUrlPath(this.createURLWithPort("/"));
    	
    	ResponseEntity<String> response = 
        		this.restTemplate.exchange(url.getShortUrl(), HttpMethod.GET, new HttpEntity<Object>(null, null), String.class);
    	
    	assertTrue(response.getStatusCode().toString().contains(String.valueOf(this.redirectType)));
    	assertTrue(response.getHeaders().get(HttpHeaders.LOCATION).get(0).equals(this.urlAddress));
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    
}
