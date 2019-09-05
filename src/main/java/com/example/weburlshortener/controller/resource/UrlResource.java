package com.example.weburlshortener.controller.resource;

import java.util.HashMap;
import java.util.List;

import com.example.weburlshortener.model.Url;

public class UrlResource extends BaseResource {

	public static HashMap<String, Object> getUrlSuccessfullyCreatedPayload(Url url) {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("shortUrl", url.shortKey);
		
		return payload;
	}
	
	public static HashMap<String, Object> getUrlStatisticsPayload(List<Url> urls) {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		for(Url url : urls) {
			payload.put(url.address, url.totalHits);
		}
		
		return payload;
	}
	
}
