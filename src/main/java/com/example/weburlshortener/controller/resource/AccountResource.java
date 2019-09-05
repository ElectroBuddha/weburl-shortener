package com.example.weburlshortener.controller.resource;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Account;

public class AccountResource extends BaseResource {
	
	public static HashMap<String, Object> makeSuccessPayload(Account account) {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("success", true);
		payload.put("description", "Account created successfully.");
		payload.put("password", account.getPassword());
		
		return payload;
	}


}
