package com.example.weburlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.weburlshortener.exception.EntityAlreadyExists;
import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	protected AccountRepository repo;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	public Account createAccount(String username, String password) throws Exception {
		String passwordEncrypted = this.passwordEncoder.encode(password);
		Account account = new Account(username, passwordEncrypted);
		
		try {
			account = this.repo.save(account);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntityAlreadyExists(null, e);
		}
		
		return account;
	}

}
