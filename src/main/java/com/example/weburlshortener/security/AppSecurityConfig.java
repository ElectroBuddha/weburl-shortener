package com.example.weburlshortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.httpBasic()
        	.and()
            .authorizeRequests()
            .antMatchers("/register").hasAnyRole("USER", "ADMIN")
            .antMatchers("/statistic").hasAnyRole("USER", "ADMIN")
            .antMatchers("/statistic/**").hasRole("ADMIN")
            .and()
            .csrf().disable();
        
        // Don't require basic auth for H2 console
        httpSecurity
    		.authorizeRequests().antMatchers("/h2_console/**").permitAll()
        	.and().headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication)
            throws Exception
    {
        authentication
        	.userDetailsService(userDetailsService);
    }

}
