package org.social.app.controller;

import org.social.app.model.response.ProfileResponseModel;
import org.social.app.securityConfigurations.UserPrincipal;
import org.social.app.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	
	@GetMapping(path = "/{profileUserUid}")
	public ResponseEntity<ProfileResponseModel> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@PathVariable String profileUserUid) {
		String loggedInUserUid = userPrincipal.getUserUid();
		ProfileResponseModel payload = profileService.getProfile(loggedInUserUid, profileUserUid);
		return ResponseEntity.ok(payload);
	}

}
