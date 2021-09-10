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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.social.app.entity.UserEntity;
import org.social.app.model.request.UserLoginRequestModel;
import org.social.app.service.UserService;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private Environment env;
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.env = env;
		this.userService = userService;
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

		UserPrincipal user = (UserPrincipal) auth.getPrincipal();//return  needs to Type cast into UserDetails class type
		String email = user.getUsername();

		UserEntity userEntity = userService.getUserByEmail(email);

		String token = Jwts.builder().setSubject(userEntity.getUserId())
				.setExpiration(
						new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		log.info(token);

		token = env.getProperty("authorization.token.header.prefix") + token;

		response.addHeader(env.getProperty("authorization.token.header.name"), token);
		response.addHeader("userId", userEntity.getUserId());

	}

}
