package org.social.app.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.User;
import org.social.app.exception.UserAlreadyExistsException;
import org.social.app.model.request.UserDetailsRequestModel;
import org.social.app.model.response.UserDetailsResponseModel;
import org.social.app.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	@PostMapping(path = "/sign-up")
	public ResponseEntity<UserDetailsResponseModel> createUser(
			@RequestBody @Valid UserDetailsRequestModel userDetailsRequestModel) {

		String email = userDetailsRequestModel.getEmail();
		if (userService.isEmailExists(email)) {
			throw new UserAlreadyExistsException("Already Exists !!! Try other!!!");
		}

		User user = userService.createUser(userDetailsRequestModel);

		UserDetailsResponseModel userDetailsResponseModel = new UserDetailsResponseModel();
		BeanUtils.copyProperties(user, userDetailsResponseModel);
		userDetailsResponseModel.setUserId(user.getUserUid());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
				.buildAndExpand(user.getUserUid()).toUri();

		return ResponseEntity.created(location).body(userDetailsResponseModel);

	}

	@GetMapping(path = "/get-user/{userId}")
	public String getUser() {
		return "get user is called!";
	}

	@PutMapping(path = "/update-user/{userId}")
	public String updateUser() {
		return "update user is called!";
	}

	@DeleteMapping(path = "/delete-user")
	public String deleteUser() {
		return "delete user is called!";
	}
	
}