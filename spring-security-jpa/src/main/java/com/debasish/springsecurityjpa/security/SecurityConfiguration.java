package com.debasish.springsecurityjpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	// This is the Builder pattern and the method chaining in effect
	
	// For authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { // Set your configuration on the auth object
		auth.userDetailsService(userDetailsService);
	}

	// For encoding the password
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // NOTE: Never use this in real world app. This means no encoding required, password will be just in clear text.
	}
	
	// For authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").hasAnyRole("USER", "ADMIN")
			.antMatchers("/user").hasRole("USER")
			.antMatchers("/**").permitAll() // configures the path, ** means configures all path in current level and the paths below this
			.and().formLogin();
	}

}