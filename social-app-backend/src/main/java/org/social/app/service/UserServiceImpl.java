package org.social.app.service;

import java.util.UUID;

import org.social.app.entity.User;
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
	public boolean isUserUidExists(String userId) {
		return userRepository.existsByUserUid(userId);
	}

	@Override
	public User createUser(UserDetailsRequestModel userDetailsRequestModel) {
		
		User user = new User();
		BeanUtils.copyProperties(userDetailsRequestModel, user);
		
		user.setUserUid(UUID.randomUUID().toString());
		
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailsRequestModel.getPassword()));
		
		user.setEmailVerificationStatus(true);
		
		user.setEmailVerificationToken("");

		// creating a user with user power
		//RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");

		//Collection<RoleEntity> roles = new HashSet<RoleEntity>();
		//roles.add(roleEntity);

		//userEntity.setRoles(roles);

		User createdUser = userRepository.saveAndFlush(user);

		return createdUser;
	}

	/*
	 * Prevent new User to login without emailVerification
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("user is not exists!!! with email: " + username);
				
		return new UserPrincipal(user);

	}

	

}
