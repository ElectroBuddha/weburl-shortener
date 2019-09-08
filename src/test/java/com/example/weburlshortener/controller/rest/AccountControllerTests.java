package com.example.weburlshortener.controller.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.weburlshortener.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD) // Flush database before each test
public class AccountControllerTests {
	
	@Autowired
	protected AccountService accountService;
	
	@LocalServerPort
	protected int port;
	
	protected TestRestTemplate restTemplate = new TestRestTemplate();
    
	protected HttpHeaders headers = new HttpHeaders();
	
	protected String username = "user1";
	protected String password = "abc123";
    
    @Before
    public void setup() {
    	headers.set("Content-Type", "application/json");
    	headers.set("Accept", "application/json");
    }
    
    @Test
    public void testCanCreateAccount() throws Exception {	
    	HashMap<String, Object> body = new HashMap<>();
    	body.put("accountId", this.username);

        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/account"), HttpMethod.POST, this.createHttpEntity(body, this.headers), String.class);
        
        assertEquals("201 CREATED", response.getStatusCode().toString());
        assertTrue(response.getBody().contains("password"));
    } 
    
    @Test
    public void testCanNotCreateAccountIfItAlreadyExists() throws Exception {
    	// Create test user directly via service
		this.accountService.createAccount(this.username, this.password);
    	
    	HashMap<String, Object> body = new HashMap<>();
    	body.put("accountId", this.username);
    	
        ResponseEntity<String> response = 
        		this.restTemplate.exchange(createURLWithPort("/account"), HttpMethod.POST, this.createHttpEntity(body, this.headers), String.class);
        
        assertEquals("409 CONFLICT", response.getStatusCode().toString());
        assertTrue(response.getBody().contains("Account already exists"));
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
