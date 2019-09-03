package com.example.weburlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.data.AccountRepository;

@RestController
public class AccountController {

	@Autowired
	AccountRepository repo;
	
	
}
