package com.example.weburlshortener.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.weburlshortener.data.UrlRepository;
import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Url;

@Service
public class UrlService {
	
	@Autowired
	UrlRepository repo;
	
	public Url createUrl(int accountId, String address, int redirectType, String shortKey) throws Exception {

		String urlShortKey = shortKey != null ? shortKey : this.generateKey();
		Url url = new Url(accountId, address, redirectType, urlShortKey);

		try {
			return this.repo.save(url);
		}
		catch(DataIntegrityViolationException e) {
			// Simple 'retry-once' workaround
			// In case 'generateKey()' produced value that already exist in the db, try one more time with new value
			if (shortKey == null) {
				return this.createUrl(accountId, address, redirectType, this.generateKey());
			}
			throw new EntityAlreadyExists(null, e);
		}
	}
	
	public List<Url> getUrls(int accountId) {
		
		List<Url> urls = this.repo.findByAccountId(accountId);
		
		return urls;
	}
	
	private String generateKey() {
		int length = 6;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}
 
}
