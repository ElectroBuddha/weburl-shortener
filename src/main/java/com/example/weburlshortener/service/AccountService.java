package com.example.weburlshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.weburlshortener.data.AccountRepository;
import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Account;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository repo;
	
	public Account createAccount(String username) throws Exception {
		
		String password = this.generatePassword();
		Account account = new Account(username, password);
		
		try {
			return this.repo.save(account);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntityAlreadyExists(null, e);
		}
	}
	
	private String generatePassword() {
		int length = 8;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}

}
