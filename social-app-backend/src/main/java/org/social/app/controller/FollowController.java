package org.social.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Follow;
import org.social.app.exception.UserNotFoundException;
import org.social.app.model.response.FollowResponseModel;
import org.social.app.securityConfigurations.UserPrincipal;
import org.social.app.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/follow")
public class FollowController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FollowService followService;
	
	
	//Future enhancement /{}
	@GetMapping(path = "/{profileUid}/addFollowing")
	public ResponseEntity<FollowResponseModel> addFollowing(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("profileUid") String profileUid) {
		
		System.out.println("\n***************"+ "addFollowing get invoked!!!");
		
		String loggedInUserUid = userPrincipal.getUserUid();
		
		System.out.println("\n**********loggedInUserUid******************"+ loggedInUserUid);
		
		Follow follow = followService.addFollowing(loggedInUserUid, profileUid);
		
		if(follow != null) {
			FollowResponseModel followResponseModel = new FollowResponseModel();
			followResponseModel.setIsFollowing(true);
			followResponseModel.setFollowerId(loggedInUserUid);
			followResponseModel.setFollowingId(profileUid);
			
			return ResponseEntity.ok(followResponseModel);
		}else {
			throw new UserNotFoundException("No such user is found!");
		}
		
	}
	
	@GetMapping(path = "/{profileUid}/removeFollowing")
	public ResponseEntity<Object> removeFollowing(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("profileUid") String profileUid) {
		
		System.out.println("\n***************"+ "removeFollowing get invoked!!!");
		
		String loggedInUserUid = userPrincipal.getUserUid();
		
		System.out.println("\n**********loggedInUserUid******************"+ loggedInUserUid);
		
		Follow follow = followService.removeFollowing(loggedInUserUid, profileUid);
	
		String message  = "removed Following done!";
		
		return ResponseEntity.ok(message);
		
	}
	
	
	
	
}
