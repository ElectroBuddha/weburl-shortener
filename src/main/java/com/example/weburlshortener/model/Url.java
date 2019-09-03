package com.example.weburlshortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Url {
	
	@Id
	@GeneratedValue
	public int id;
	
	@Column(unique=true)
	public String url;
	
	@NotNull
	public int redirectType;
	
	@NotNull
	public int totalHits = 0;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getRedirectType() {
		return redirectType;
	}
	
	public void setRedirectType(int redirectType) {
		this.redirectType = redirectType;
	}
	
	public int getTotalHits() {
		return totalHits;
	}
	
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

}
