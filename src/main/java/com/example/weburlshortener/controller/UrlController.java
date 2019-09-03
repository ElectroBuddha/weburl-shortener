package com.example.weburlshortener.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weburlshortener.data.UrlRepository;
import com.example.weburlshortener.model.Url;

@RestController
public class UrlController {
	
	@Autowired
	UrlRepository repo;

	@GetMapping("/statistic")
	public HashMap<String,Integer> index() {
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		
		List<Url> urls = repo.findAll(Sort.by(Sort.Direction.DESC, "totalHits"));

		for (Url url : urls) {
			result.put(url.url, url.totalHits);
		}
		
		return result;
	}

}
