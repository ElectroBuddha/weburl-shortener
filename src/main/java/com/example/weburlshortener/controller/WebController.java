package com.example.weburlshortener.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebController {

	
	@GetMapping("/{key}")
	public ResponseEntity<?> redirect(@PathVariable String key) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		responseHeaders.set("Location", "http://www.google.com");
				
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
		      .headers(responseHeaders)
		      .build();
		
	}
	
	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	
	@RequestMapping("/help")
	public String help() {
		
		return "help";
	}
 	
}
