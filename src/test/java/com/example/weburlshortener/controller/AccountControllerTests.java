package com.example.weburlshortener.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTests {
	
	@LocalServerPort
    private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
    
    HttpHeaders headers = new HttpHeaders();
    
    @Test
    public void testCreateAccount() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = 
        	restTemplate.exchange(
        		createURLWithPort("/account"), 
        		HttpMethod.POST, entity, 
        		String.class
        	);
       
        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        
        assertTrue(actual.contains("/account"));
    } 
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
