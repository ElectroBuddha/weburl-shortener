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

@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	
	@NotNull
	protected int accountId;
	
	@NotNull
	protected String address;
	
	@Min(301)
	@Max(302)
	protected int redirectType;
	
	@Column(unique=true)
	protected String shortKey;
	
	@Min(0)
	protected int numOfHits = 0;
	
	@Transient
	protected String baseUrlPath;

	public Url() {
	}
	
	public Url(int accountId, String address, int redirectType, String shortKey, String baseUrlPath) {
		this.setAccountId(accountId);
		this.setAddress(address);
		this.setRedirectType(redirectType);
		this.setShortKey(shortKey);
		this.setBaseUrlPath(baseUrlPath);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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

	public int getNumOfHits() {
		return numOfHits;
	}
	
	public void setNumOfHits(int numOfHits) {
		this.numOfHits = numOfHits;
	}
	
	public String getBaseUrlPath() {
		return baseUrlPath;
	}

	public void setBaseUrlPath(String baseUrlPath) {
		this.baseUrlPath = baseUrlPath;
	}

	public String getShortUrl() {
		String shortUrl;
		
		if (this.baseUrlPath != null) {
			String basePath = baseUrlPath.endsWith("/") ? baseUrlPath : baseUrlPath + "/";
			shortUrl = basePath + this.getShortKey();
		}
		else {
			shortUrl = this.getShortKey();
		}
		
		return shortUrl;
	}

}
