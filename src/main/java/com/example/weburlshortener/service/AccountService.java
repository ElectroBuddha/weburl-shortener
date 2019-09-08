package com.example.weburlshortener.service;

import com.example.weburlshortener.model.Account;

public interface AccountService {
	
	public Account createAccount(String username, String password) throws Exception;
	
	public Account getAccountByUsername(String username);

}
