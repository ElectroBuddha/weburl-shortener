package com.example.weburlshortener.controller.rest.model;

import javax.validation.constraints.NotNull;

public class AccountCreateRequest {

	@NotNull(message="This is a required field")
	protected String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
