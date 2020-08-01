
package com.appsdeveloperblog.app.ws.mobileappws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCrptPasswordEncoder;

	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bcrptPasswordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCrptPasswordEncoder = bcrptPasswordEncoder;
	}

	// CODE TO RESTICT ONLY POST CAN BE PUBLIC ANOTHER REQUEST WILL BE PROTECTED
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstant.SIGNUP_URL).permitAll()
				.anyRequest().authenticated()
		// ADDING
		// THE AUTHENTICATION FILTER CLASS
		 .and().addFilter(getAuthenticationFilter()).addFilter(new
		AuthorizationFilter(authenticationManager()))
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// CODE TO HELP TO LOAD USER DETAILS FROM DATABASE// SAVE BYCRYPT VALUE IN
	// DATABASE//
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCrptPasswordEncoder);
	}

	// CODE FOR MODIFYING DEFAULT LOGIN URL
	 public AuthenticationFilter getAuthenticationFilter() throws Exception {
	  final AuthenticationFilter filter = new
	 AuthenticationFilter(authenticationManager());
	 filter.setFilterProcessesUrl("/users/login"); return filter; }
	 
}
