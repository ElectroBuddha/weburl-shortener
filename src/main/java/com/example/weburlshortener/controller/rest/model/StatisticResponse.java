package com.example.weburlshortener.controller.rest.model;

import java.util.HashMap;
import java.util.List;

import com.example.weburlshortener.model.Url;

public class StatisticResponse {
	
	public static HashMap<String, Object> ok(List<Url> urls) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		for(Url url : urls) {
			// If multiple records exist for the same url address, add their values to the single hashmap key
			int currentCount = map.get(url.getAddress()) != null ? (int) map.get(url.getAddress()) : 0;
			map.put(url.getAddress(), currentCount + url.getNumOfHits());
		}
		
		return map;
	}
	
	public static HashMap<String, Object> error(String message) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("success", false);
		map.put("description", message);
		
		return map;
    }

}
