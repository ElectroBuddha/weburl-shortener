package com.example.weburlshortener.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.weburlshortener.data.UrlRepository;
import com.example.weburlshortener.exceptions.EntityAlreadyExists;
import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.util.Utils;

@Service
public class UrlService {
	
	@Autowired
	protected UrlRepository repo;
	
	public Url createUrl(int accountId, String address, int redirectType, String shortKey) throws Exception 
	{
		String urlShortKey = shortKey != null ? shortKey : this.generateKey();
		Url url = new Url(accountId, address, redirectType, urlShortKey);

		try {
			return this.repo.save(url);
		}
		catch(DataIntegrityViolationException e) {
			// Simple 'retry-once' workaround
			// In case 'generateKey()' produced value that already exist in the db, try one more time with another value
			if (shortKey == null) {
				return this.createUrl(accountId, address, redirectType, this.generateKey());
			}
			throw new EntityAlreadyExists(null, e);
		}
	}

	public List<Url> getUrlsByAccountId(int accountId) 
	{
		List<Url> urls = this.repo.findByAccountId(accountId);
		
		return urls;
	}
	
	public List<Url> getAllUrls() 
	{
		List<Url> urls = this.repo.findAll();
		
		return urls;
	}
	
	@Transactional
	public Url getUrlByShortKeyAndIncrementTopHits(String key)
	{
		Url url;
		
		try {
			url = this.repo.findByShortKeyForWrite(key);
			
			url.setTotalHits(url.getTotalHits() + 1);
	
			this.repo.save(url);
		}
		// Catch PessimisticLockException (or any other exception type)
		catch(Exception e) {
			System.out.println(e.getMessage());
			url = null;
		}
		
		return url;
	}
	
	protected String generateKey() 
	{
		int length = 6;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}
 
}
