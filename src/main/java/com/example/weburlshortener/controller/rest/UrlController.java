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
	
	/**
	 * Create short url for submitted url
	 * 
	 * @param principal
	 * @param urlRegisterRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> registerUrl(Principal principal, @RequestBody @Valid UrlRegisterRequest urlRegisterRequest) throws Exception  {	
		ResponseEntity<?> response;
		
		try {
			Account authenticatedAccount = this.accountRepo.findByUsername(principal.getName());	
			
			Url url = this.urlService.createUrl(authenticatedAccount.getId(), urlRegisterRequest.getUrl(), urlRegisterRequest.getRedirectType());
			url.setBaseUrlPath(this.appProperties.getBaseUrlPath());
			
			response = new ResponseEntity<>(UrlRegisterResponse.ok(url), HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new Exception("Problem registering url, please try again!", e);
		}
		
		return response;
	}
	
	/**
	 * Retrieve url statistics for authenticated user
	 * 
	 * @param principal
	 * @return
	 */
	@GetMapping(value = {"/statistic"}, produces = "application/json")
	private ResponseEntity<Object> getOwnStatistics(Principal principal) {

		Account authenticatedAccount = this.accountRepo.findByUsername(principal.getName());	
		
		List<Url> urls = authenticatedAccount.isAdmin() 
					? this.urlService.getAllUrls()
				    : this.urlService.getUrlsByAccountId(authenticatedAccount.getId());
					
		return new ResponseEntity<>(StatisticResponse.ok(urls), HttpStatus.OK);
	}
	
	/**
	 * Only available to users with 'ADMIN' role
	 * 
	 * @param principal
	 * @param accountId
	 * @return
	 */
	@GetMapping(value = {"/statistic/{accountId}"}, produces = "application/json")
	private ResponseEntity<Object> getStatisticsByAccountId(@PathVariable String accountId) {
	
		Account requestedAccount = this.accountRepo.findByUsername(accountId);
		
		if (requestedAccount == null) {
			return new ResponseEntity<>(StatisticResponse.error("AccountId does not exist."), HttpStatus.PRECONDITION_FAILED);
		}
		
		List<Url> urls = this.urlService.getUrlsByAccountId(requestedAccount.getId());
		return new ResponseEntity<>(StatisticResponse.ok(urls), HttpStatus.OK);
	}


}
