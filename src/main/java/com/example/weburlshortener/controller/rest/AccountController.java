package com.example.weburlshortener.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.controller.rest.model.AccountCreateRequest;
import com.example.weburlshortener.controller.rest.model.AccountCreateResponse;
import com.example.weburlshortener.exception.EntityAlreadyExists;
import com.example.weburlshortener.service.AccountServiceImpl;
import com.example.weburlshortener.util.Utils;


@RestController
public class AccountController {
	
	@Autowired
	protected AccountServiceImpl service;

	/**
	 * Create account with submitted account id
	 * 
	 * @param accountCreateRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/account", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest) throws Exception {
		ResponseEntity<Object> response;
		
		try {
			String accountId = accountCreateRequest.getAccountId();
			String password = Utils.getRandomStringForAccountPassword();
			
			this.service.createAccount(accountId, password);
			
			response = new ResponseEntity<>(AccountCreateResponse.ok(password), HttpStatus.CREATED);
		}
		catch(EntityAlreadyExists e) {
			response = new ResponseEntity<>(AccountCreateResponse.duplicateEntity(), HttpStatus.CONFLICT);
		}
		
		return response;
	}
	
}
