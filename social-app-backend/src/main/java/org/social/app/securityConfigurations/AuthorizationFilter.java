package org.social.app.securityConfigurations;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import org.social.app.entity.UserEntity;
import org.social.app.service.UserService;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private Environment env;
	private UserService userService;

	@Autowired
	public AuthorizationFilter(AuthenticationManager authManager, Environment environment, UserService userService) {
		super(authManager);
		this.env = environment;
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

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {

		String authorizationHeader = req.getHeader(env.getProperty("authorization.token.header.name"));

		if (authorizationHeader == null) {
			return null;
		}

		String token = authorizationHeader.replace(env.getProperty("authorization.token.header.prefix"), "");

		String userId = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token)
				.getBody().getSubject();

		if (userId == null) {
			return null;
		}

		UserEntity userEntity = userService.getUserByUserId(userId);
		UserPrincipal user = new UserPrincipal(userEntity);

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

	}
}
