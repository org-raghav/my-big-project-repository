package org.social.app.securityConfigurations;

import org.social.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	
	public static final String[] MATCHERS = {"/public/**",
			"/h2-console/**"
	};
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.cors().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers(MATCHERS).permitAll()
		.antMatchers(HttpMethod.POST, env.getProperty("signUp.url.path")).permitAll()
		.antMatchers(HttpMethod.GET, env.getProperty("email.verification.url")).permitAll()
		.antMatchers(HttpMethod.POST, env.getProperty("password.reset.url")).permitAll()
		.antMatchers(HttpMethod.GET, env.getProperty("password.verification.url")).permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(getAuthenticationFilter())
		.addFilter(new AuthorizationFilter(authenticationManager(), env, userService))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(userService, env, authenticationManager());
		authFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authFilter;
	}

}
