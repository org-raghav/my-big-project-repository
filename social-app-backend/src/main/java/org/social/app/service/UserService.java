package org.social.app.service;

import org.social.app.entity.User;
import org.social.app.model.request.UserDetailsRequestModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

	public User createUser(UserDetailsRequestModel userDetailsRequestModel);
	
	public boolean isEmailExists(String email);

	public boolean isUserUidExists(String userId);	
	
}
