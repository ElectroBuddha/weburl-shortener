package com.example.weburlshortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.weburlshortener.model.Account;
import com.example.weburlshortener.repository.AccountRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	protected AccountRepository accountRepo;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Account account = this.accountRepo.findByUsername(username);
		
		if (account == null) {
            throw new UsernameNotFoundException(username);
        }
		
        return new AppUserDetails(account);
	}

}
