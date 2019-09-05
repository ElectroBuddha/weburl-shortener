package com.example.weburlshortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

	@Id
	@GeneratedValue
	public int id;
	
	@NotNull
	@Column(unique=true)
	public String username;
	
	@NotNull
	public String password;
	
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
	
}
