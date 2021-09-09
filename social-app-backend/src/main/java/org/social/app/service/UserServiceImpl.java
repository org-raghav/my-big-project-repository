package org.social.app.service;

import java.util.UUID;

import org.social.app.entity.UserEntity;
import org.social.app.model.request.UserDetailsRequestModel;
import org.social.app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isUserIdExists(String userId) {
		return userRepository.existsByUserId(userId);
	}

	@Override
	public UserEntity createUser(UserDetailsRequestModel userDetailsRequestModel) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDetailsRequestModel, userEntity);
		
		userEntity.setUserId(UUID.randomUUID().toString());
		
		userEntity.setEncryptedPassword(userDetailsRequestModel.getPassword());
		
		userEntity.setEmailVerificationStatus(true);
		
		userEntity.setEmailVerificationToken("");

		// creating a user with user power
		//RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");

		//Collection<RoleEntity> roles = new HashSet<RoleEntity>();
		//roles.add(roleEntity);

		//userEntity.setRoles(roles);

		UserEntity createdUser = userRepository.saveAndFlush(userEntity);

		return createdUser;
	}
	
	

}
