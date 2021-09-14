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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.social.app.entity.User;
import org.social.app.service.UserService;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private Environment env;

	@Autowired
	public AuthorizationFilter(AuthenticationManager authManager, Environment env) {
		super(authManager);
		this.env = env;
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
		
		String jwt = authorizationHeader.replace(env.getProperty("authorization.token.header.prefix"), "");
		
		Claims claims = Jwts.parser()         
				   .setSigningKey(env.getProperty("token.secret"))
				   .parseClaimsJws(jwt).getBody();

		//claims.getId();claims.getAudience();claims.getIssuer();etc...
		
		UserPrincipal userPrincipal = claims.get("user", UserPrincipal.class);
		
		return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

	}
}
