package org.social.app.service;

import org.social.app.entity.UserEntity;
import org.social.app.model.request.UserDetailsRequestModel;

public interface UserService {

	public boolean isEmailExists(String email);
	
	public boolean isUserIdExists(String publicUserId);

	public UserEntity createUser(UserDetailsRequestModel userDetailsRequestModel);
	
	
}
