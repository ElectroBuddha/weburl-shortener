package com.example.weburlshortener.controller.rest;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.config.AppProperties;
import com.example.weburlshortener.controller.rest.model.StatisticResponse;
import com.example.weburlshortener.controller.rest.model.UrlRegisterRequest;
import com.example.weburlshortener.controller.rest.model.UrlRegisterResponse;
import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.repository.AccountRepository;
import com.example.weburlshortener.service.UrlServiceImpl;

@RestController
public class UrlController {
	
	@Autowired
	protected UrlServiceImpl urlService;
	
	@Autowired
	protected AccountRepository accountRepo;
	
	@Autowired
	protected AppProperties appProperties;
	
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> registerUrl(Principal principal, @RequestBody @Valid UrlRegisterRequest urlRegisterRequest) throws Exception  {	
		ResponseEntity<?> response;
		
		try {
			Account authorizedAccount = this.accountRepo.findByUsername(principal.getName());	
			
			Url url = this.urlService.createUrl(authorizedAccount.getId(), urlRegisterRequest.getUrl(), urlRegisterRequest.getRedirectType());
			url.setBaseUrlPath(this.appProperties.getBaseUrlPath());
			
			response = new ResponseEntity<>(UrlRegisterResponse.ok(url), HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new Exception("Problem registering url, please try again!", e);
		}
		
		return response;
	}
	
	@GetMapping(value = {"/statistic"}, produces = "application/json")
	private ResponseEntity<Object> getOwnStatistics(Principal principal) {

		Account authorizedAccount = this.accountRepo.findByUsername(principal.getName());	
		
		List<Url> urls = authorizedAccount.isAdmin() 
					? this.urlService.getAllUrls()
				    : this.urlService.getUrlsByAccountId(authorizedAccount.getId());
					
		return new ResponseEntity<>(StatisticResponse.ok(urls), HttpStatus.OK);
	}
	
	@GetMapping(value = {"/statistic/{accountId}"}, produces = "application/json")
	private ResponseEntity<Object> getStatisticsByAccountId(Principal principal, @PathVariable String accountId) {
		
		Account authorizedAccount = this.accountRepo.findByUsername(principal.getName());	
		
		// Only admins can view 'per-user' statistics
		if (!authorizedAccount.isAdmin()) {
			return new ResponseEntity<>(StatisticResponse.error("You can only view your own statistics."), HttpStatus.PRECONDITION_FAILED);
		}
		
		Integer resolvedAccountId = this.resolveAccountId(accountId);
		
		if (resolvedAccountId == null) {
			return new ResponseEntity<>(StatisticResponse.error("AccountId does not exist."), HttpStatus.PRECONDITION_FAILED);
		}
		
		List<Url> urls = this.urlService.getUrlsByAccountId(resolvedAccountId);
		return new ResponseEntity<>(StatisticResponse.ok(urls), HttpStatus.OK);
	}
	
	private Integer resolveAccountId(String username) {
		Account account = this.accountRepo.findByUsername(username);
		
		if (account != null) {
			return account.getId();
		}
		
		return null;
	}

}
