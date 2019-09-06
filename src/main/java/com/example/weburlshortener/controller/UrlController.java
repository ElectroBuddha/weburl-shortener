package com.example.weburlshortener.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.controller.resource.UrlResource;
import com.example.weburlshortener.data.AccountRepository;
import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.service.UrlService;

@RestController
public class UrlController extends BaseController {
	
	@Autowired
	protected UrlService urlService;
	
	@Autowired
	protected AccountRepository accountRepo;

	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> registerUrl(Principal principal, @RequestBody @Nullable HashMap<String, Object> requestPayload) throws Exception 
	{	
		// Simple validation
		if (requestPayload == null || requestPayload.get("url") == null || requestPayload.get("redirectType") == null) {
			return makeResponse(
					UrlResource.makeErrorPayload("Missing required param. Please provide valid 'url' and 'redirectType'."), 
					HttpStatus.UNPROCESSABLE_ENTITY
			);
		}

		ResponseEntity<?> response;
		String urlAddress = (String) requestPayload.get("url");
		int redirectType  = (int) requestPayload.get("redirectType");
		Account account   = this.accountRepo.findByUsername(principal.getName());	
		
		if (redirectType != 301 && redirectType != 302) {
			return makeResponse(
					UrlResource.makeErrorPayload("Param 'redirectType' must be equal to 301 or 302"), 
					HttpStatus.UNPROCESSABLE_ENTITY
			);
		}
		
		if (account == null) {
			return makeResponse(
					UrlResource.makeErrorPayload("No account found."), 
					HttpStatus.UNPROCESSABLE_ENTITY
			);
		}
		
		try {
			Url url = this.urlService.createUrl(account.getId(), urlAddress, redirectType, null);
			response = makeResponse(UrlResource.getUrlSuccessfullyCreatedPayload(url), HttpStatus.CREATED);
		}
		catch(Exception e) {
			response = makeResponse(UrlResource.makeErrorPayload("Problem registering url, please try again!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return response;
	}
	
	@GetMapping(value = {"/statistic", "/statistic/{accountId}"}, produces = "application/json")
	public ResponseEntity<?> getStatistics(Principal principal, @PathVariable(required = false) Integer accountId) 
	{
		List<Url> urls;
		
		Account account = this.accountRepo.findByUsername(principal.getName());	
		
		if (account.isAdmin()) {
			urls = accountId != null 
					? this.urlService.getUrlsByAccountId(accountId)
					: this.urlService.getAllUrls();
		}
		else if (accountId == null) {
			urls = this.urlService.getUrlsByAccountId(account.getId());
		}
		else {
			return makeResponse(
					UrlResource.makeErrorPayload("You can only view your own statistics."), 
					HttpStatus.FORBIDDEN
			);
		}

		return makeResponse(UrlResource.getUrlStatisticsPayload(urls), HttpStatus.OK);
	}

}