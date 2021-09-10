package org.social.app.service;

import java.util.UUID;

import org.social.app.entity.UserEntity;
import org.social.app.model.request.UserDetailsRequestModel;
import org.social.app.repository.UserRepository;
import org.social.app.securityConfigurations.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailsRequestModel.getPassword()));
		
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

	/*
	 * Prevent new User to login without emailVerification
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException("user is not exists!!! with email: " + username);
				
		return new UserPrincipal(userEntity);

	}

	@Override
	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserEntity getUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	

}
