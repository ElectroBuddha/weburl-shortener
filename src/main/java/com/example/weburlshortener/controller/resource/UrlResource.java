package com.example.weburlshortener.controller.resource;

import java.util.HashMap;
import java.util.List;

import com.example.weburlshortener.model.Url;

public class UrlResource extends BaseResource {
	
	public static HashMap<String, Object> getUrlSuccessfullyCreatedPayload(Url url) 
	{
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("shortUrl", url.getShortUrl());
		
		return payload;
	}
	
	public static HashMap<String, Object> getUrlStatisticsPayload(List<Url> urls) 
	{
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		for(Url url : urls) {
			// If multiple records exist for the same url address, add their values to the single hashmap key
			int currentCount = payload.get(url.getAddress()) != null ? (int) payload.get(url.getAddress()) : 0;
			payload.put(url.getAddress(), currentCount + url.getTotalHits());
		}
		
		return payload;
	}
	
}
