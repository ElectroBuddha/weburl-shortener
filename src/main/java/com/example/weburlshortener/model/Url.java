package com.example.weburlshortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.weburlshortener.util.ConfigProperties;

@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@NotNull
	protected int accountId;
	
	@NotNull
	protected String address;
	
	@NotNull
	@Min(301)
	@Max(302)
	protected int redirectType;
	
	@NotNull
	@Column(unique=true)
	protected String shortKey;
	
	@NotNull
	protected int totalHits = 0;
	
	@Transient
	ConfigProperties configProperties;

	public Url() {
	}
	
	public Url(int accountId, String address, int redirectType, String shortKey, ConfigProperties configProperties) {
		this.setAccountId(accountId);
		this.setAddress(address);
		this.setRedirectType(redirectType);
		this.setShortKey(shortKey);
		this.setConfigProperties(configProperties);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getRedirectType() {
		return redirectType;
	}
	
	public void setRedirectType(int redirectType) {
		this.redirectType = redirectType;
	}
	
	public String getShortKey() {
		return shortKey;
	}

	public void setShortKey(String shortKey) {
		this.shortKey = shortKey;
	}

	public int getTotalHits() {
		return totalHits;
	}
	
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
	}
	
	public String getShortUrl() {
		String shortUrl;
		
		if (this.configProperties != null) {
			String baseUrlPath = this.configProperties.getBaseUrlPath();
			String basePath = baseUrlPath.endsWith("/") ? baseUrlPath : baseUrlPath + "/";
			shortUrl = basePath + this.getShortKey();
		}
		else {
			shortUrl = this.getShortKey();
		}
		
		return shortUrl;
	}

}
