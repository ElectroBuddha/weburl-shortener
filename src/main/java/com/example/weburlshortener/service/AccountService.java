package com.example.weburlshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.weburlshortener.data.AccountRepository;
import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Account;

@Service
public class AccountService {
	
	@Autowired
	protected AccountRepository repo;
	
	public String createAccount(String username) throws Exception {
		
		String password = this.generatePassword();
		String passwordEncrypted = this.passwordEncoder().encode(password);
		Account account = new Account(username, passwordEncrypted);
		
		try {
			this.repo.save(account);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntityAlreadyExists(null, e);
		}
		
		return password;
	}
	
	protected String generatePassword() {
		int length = 8;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	
	protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
