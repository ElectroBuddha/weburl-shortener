package com.example.weburlshortener.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.service.UrlServiceImpl;


@Controller
public class IndexController {
	
	@Value("${app.homeUrlPath}")
	protected String homeUrlPath;
	
	@Autowired
	protected UrlServiceImpl urlService;

	@GetMapping("/{key}")
	public ResponseEntity<?> redirect(@PathVariable String key) 
	{
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.MOVED_PERMANENTLY;
		
		Url url = this.urlService.getUrlByShortKeyAndIncrementTopHits(key);
		
		// Redirect to homepage if url was not found
		if (url == null) {
			responseHeaders.set("Location", this.homeUrlPath);
			status = HttpStatus.MOVED_PERMANENTLY;
		}
		else {
			responseHeaders.set("Location", url.getAddress());
			status = url.getRedirectType() == 302 ? HttpStatus.MOVED_TEMPORARILY : HttpStatus.MOVED_PERMANENTLY;
		}
		
		return ResponseEntity.status(status)
		      .headers(responseHeaders)
		      .build();
		
	}
	
	@RequestMapping("/")
	public ModelAndView home() 
	{
		ModelAndView mv = new ModelAndView("home");
		
		return mv;
	}
	
	@RequestMapping("/help")
	public ModelAndView help() 
	{
		ModelAndView mv = new ModelAndView("help");
		
		return mv;
	}
 	
}
