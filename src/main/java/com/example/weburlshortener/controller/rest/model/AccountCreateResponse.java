package com.example.weburlshortener.controller.rest.model;

import java.util.HashMap;

public class AccountCreateResponse {

	public static HashMap<String, Object> ok(String password) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("success", true);
		map.put("description", "Account created successfully.");
		map.put("password", password);
		
		return map;
    }
	
	public static HashMap<String, Object> duplicateEntity() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("success", false);
		map.put("description", "Account already exists!");
		
		return map;
    }
	
}
