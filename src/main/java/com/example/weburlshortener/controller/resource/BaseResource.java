package com.example.weburlshortener.controller.resource;

import java.util.HashMap;


public class BaseResource {
	
	public static HashMap<String, Object> makeSuccessPayload(String message) {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("success", true);
		payload.put("description", message);
		
		return payload;
	}

	public static HashMap<String, Object> makeErrorPayload(String message) { 
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("success", false);
		payload.put("description", message);
		
		return payload;
	}
	
	
}
