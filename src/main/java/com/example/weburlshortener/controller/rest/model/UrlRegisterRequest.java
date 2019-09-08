package com.example.weburlshortener.controller.rest.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UrlRegisterRequest {

	@NotNull(message="This is a required field")
	protected String url;

	@Min(value = 301, message = "This should be 301 or 302")
	@Max(value = 302, message = "This should be 301 or 302")
	protected int redirectType;

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

}
