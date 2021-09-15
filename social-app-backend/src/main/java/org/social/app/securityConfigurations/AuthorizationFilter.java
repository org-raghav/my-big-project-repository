package org.social.app.securityConfigurations;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private Environment env;
	private UserService userService;
	
	@Autowired
    public AuthorizationFilter(AuthenticationManager authManager, Environment env, UserService userService) {
		super(authManager);
		this.env = env;
		this.userService = userService;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = req.getHeader(env.getProperty("authorization.token.header.name"));

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(env.getProperty("authorization.token.header.prefix"))) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		//get Authorization header contains user token
		String token = request.getHeader(env.getProperty("authorization.token.header.name"));

		if (token == null) {
			return null;
		}
		
		String jwt = token.replace(env.getProperty("authorization.token.header.prefix"), "");
		
		Claims claims = Jwts.parser()         
				   .setSigningKey(env.getProperty("token.secret"))
				   .parseClaimsJws(jwt).getBody();

		//claims.getId();claims.getAudience();claims.getIssuer();etc...
		String username = claims.getSubject();
		String userUid  = claims.getId();
		String myValue =  (String) claims.get("my-key");
		log.info("***** my-key ******  " + myValue);
		
		UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(username);
				
		log.info("***** User is successfully Authorized ******  " + username + "userUid is :: " + userUid);
		
		return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

	}
}
