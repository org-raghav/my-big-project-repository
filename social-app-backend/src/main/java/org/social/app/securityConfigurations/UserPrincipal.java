package org.social.app.securityConfigurations;

import java.util.Collection;
import java.util.HashSet;

import org.social.app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -372248885026994774L;

	private User user;

	public UserPrincipal(User user) {
		this.user = user;
	}

	public String getUserUid() {
		return user.getUserUid();
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public String getLatName() {
		return user.getLastName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>();
	}

	@Override
	public String getPassword() {
		return user.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
