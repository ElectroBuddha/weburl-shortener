package com.example.weburlshortener.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.weburlshortener.model.Account;

public class AppUserDetails implements UserDetails 
{
	private static final long serialVersionUID = -323762462513202447L;
	
	String ROLE_PREFIX = "ROLE_";
	
	protected Account account;
	
	public AppUserDetails(Account account) {
		this.account = account;
	}
	
	public String getUsername() {
		return this.account.getUsername();
	}
	
	public String getPassword() {
		return this.account.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(String role : this.account.getRoles()){
	        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
	    }
		
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
