package com.example.weburlshortener.service;

import java.util.List;

import com.example.weburlshortener.model.Url;

public interface UrlService {
	
	public Url createUrl(int accountId, String address, int redirectType);
	
	public List<Url> getUrlsByAccountId(int accountId);
	
	public List<Url> getAllUrls();
	
	public Url getUrlByShortKeyAndIncrementTopHits(String shortKey);

}
