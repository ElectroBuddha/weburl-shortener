package com.example.weburlshortener.controller.rest.model;

import java.util.HashMap;

import com.example.weburlshortener.model.Url;

public class UrlRegisterResponse {
	
	public static HashMap<String, Object> ok(Url url) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("shortUrl", url.getShortUrl());
		
		return map;
	}

}
