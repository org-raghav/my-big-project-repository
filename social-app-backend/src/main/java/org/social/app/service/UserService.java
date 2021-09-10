package org.social.app.service;

import org.social.app.entity.UserEntity;
import org.social.app.model.request.UserDetailsRequestModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

	public boolean isEmailExists(String email);
	
	public boolean isUserIdExists(String publicUserId);

	public UserEntity createUser(UserDetailsRequestModel userDetailsRequestModel);

	public UserEntity getUserByEmail(String email);
	
	public UserEntity getUserByUserId(String userId);
	
	
}
