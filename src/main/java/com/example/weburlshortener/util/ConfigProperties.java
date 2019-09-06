package com.example.weburlshortener.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "app")
public class ConfigProperties 
{	
	protected String baseUrlPath;
	protected String homeUrlPath;
	
	public String getBaseUrlPath() {
		return baseUrlPath;
	}
	
	public void setBaseUrlPath(String baseUrlPath) {
		this.baseUrlPath = baseUrlPath;
	}
	
	public String getHomeUrlPath() {
		return homeUrlPath;
	}
	
	public void setHomeUrlPath(String homeUrlPath) {
		this.homeUrlPath = homeUrlPath;
	}

	
}
