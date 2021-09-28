package org.social.app.securityConfigurations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.model.request.UserLoginRequestModel;
import org.social.app.model.response.UserLoginResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final Environment env;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AuthenticationFilter(AuthenticationManager authenticationManager, Environment env) {
		this.authenticationManager = authenticationManager;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			ServletInputStream inputStream = request.getInputStream();
			ObjectMapper objectMapper = new ObjectMapper();
			UserLoginRequestModel creds = objectMapper.readValue(inputStream, UserLoginRequestModel.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);// throw exception is important as method return Authentication object.
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();// return needs to Type cast into UserDetails
																			// class type

		Claims claims = Jwts.claims();
		claims.setIssuer("Social-Blog-Application");
		claims.setSubject(userPrincipal.getUsername());
		claims.setId(userPrincipal.getUserUid());
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
		claims.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))));
		
		//we  can put whole object here
		//but object must be type of HashMap so that in form of json object
		//claims.put("key", value);//value is of type HashMap
		claims.put("my-key", "my-value");
		
		String jwt = Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		log.info(" ****** my jwt token ********* " + jwt);

		String token = env.getProperty("authorization.token.header.prefix") + jwt;
		
		UserLoginResponseModel userData = new UserLoginResponseModel();
		userData.setFirstName(userPrincipal.getFirstName());
		userData.setLastName(userPrincipal.getLatName());
		userData.setUserId(userPrincipal.getUserUid());
		String json = new ObjectMapper().writeValueAsString(userData);
		log.info(" ****** user json data ********* " + json);

		response.getWriter().write(json);
		response.addHeader(env.getProperty("authorization.token.header.name"), token);

	}

}
