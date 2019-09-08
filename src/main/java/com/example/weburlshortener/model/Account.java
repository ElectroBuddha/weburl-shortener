package com.example.weburlshortener.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	@Column(unique=true)
	protected String username;
	
	@NotNull
	protected String password;
	
	@NotNull
	protected String roles = "USER";
	
	public Account() {	
	}
	
	public Account(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getRoles() {
		return Arrays.asList(this.roles.split(","));
	}
	
	public boolean isAdmin() {
		return Arrays.asList(this.roles.split(",")).contains("ADMIN");
	}
	
}
