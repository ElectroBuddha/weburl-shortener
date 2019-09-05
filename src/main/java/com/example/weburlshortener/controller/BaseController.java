package com.example.weburlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

	public static ResponseEntity<?> makeResponse(Object payload, HttpStatus status) {
		return new ResponseEntity<>(payload, status);
	}
	
}
