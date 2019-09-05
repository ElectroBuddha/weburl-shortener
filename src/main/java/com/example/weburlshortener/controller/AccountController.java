package com.example.weburlshortener.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.controller.resource.AccountResource;
import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.service.AccountService;


@RestController
public class AccountController extends BaseController {
	
	@Autowired
	AccountService service;
	
	@PostMapping(value = "/account", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createAccount(@RequestBody @Nullable HashMap<String, Object> requestPayload) {
		
		// Simple validation
		if (requestPayload == null || requestPayload.get("accountId") == null) {
			return makeResponse(
					AccountResource.makeErrorPayload("Missing required param. Please provide valid 'accountId'."), 
					HttpStatus.UNPROCESSABLE_ENTITY
			);
		}
		
		ResponseEntity<?> response;
		String accountId = (String) requestPayload.get("accountId");
		
		try {
			Account account = this.service.createAccount(accountId);
			response = makeResponse(AccountResource.makeSuccessPayload(account), HttpStatus.CREATED);
		}
		catch(EntityAlreadyExists error) {
			response = makeResponse(AccountResource.makeErrorPayload("Account already exists!"), HttpStatus.CONFLICT);
		}
		catch(Exception error) {
			response = makeResponse(AccountResource.makeErrorPayload("Problem creating account!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
}
