package org.social.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.model.response.FollowersResposeModel;
import org.social.app.model.response.FollowingsResponseModel;
import org.social.app.model.response.PostResponseModel;
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

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProfileService profileService;

	
	@GetMapping(path = "/{profileUserUid}")
	public ResponseEntity<ProfileResponseModel> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@PathVariable String profileUserUid) {
		String loggedInUserUid = userPrincipal.getUserUid();
		
		System.out.println("*****************************************\n");
		System.out.println(loggedInUserUid);
		System.out.println(profileUserUid);

		
		ProfileResponseModel payload = profileService.getProfile(loggedInUserUid, profileUserUid);
		return ResponseEntity.ok(payload);
	}
	
	//In future Must implement pagination for better performance.
	@GetMapping(path = "/{profileUserUid}/posts")
	public ResponseEntity<List<PostResponseModel>> getAllPostsByProfileUid(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@PathVariable String profileUserUid){
		List<PostResponseModel> payload = profileService.getAllPostByProfileUserUid(profileUserUid);
		return ResponseEntity.ok(payload);
	}
	
	//http://localhost:8080/profile/5ba0a533-94db-4e4c-84d2-460f9a87670c/followers
	@GetMapping(path = "/{profileUserUid}/followers")
	public ResponseEntity<List<FollowersResposeModel>> getFollowers(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("profileUserUid") String profileUserUid) {
		
			log.info("\n***************"+ "getFollowers() method get invoked!!!");
			
			String loggedInUserUid = userPrincipal.getUserUid();
			
			log.info("\n**********loggedInUserUid******************"+ loggedInUserUid);
		
			log.info("\n**********profileUid******************"+ profileUserUid);
			
			//always get followers of profileUserUid
			List<FollowersResposeModel> followers = profileService.getAllFollowers(profileUserUid);
			
			return ResponseEntity.ok(followers);
			
	}
	//http://localhost:8080/profile/ad8c7d62-c91c-4f57-821a-b082d278f53b/followings	
	@GetMapping(path = "/{profileUserUid}/followings")
	public ResponseEntity<List<FollowingsResponseModel>> getFollowings(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("profileUserUid") String profileUserUid) {
		
			log.info("\n***************"+ "getFollowings() method get invoked!!!");
			
			String loggedInUserUid = userPrincipal.getUserUid();
			
			log.info("\n**********loggedInUserUid******************"+ loggedInUserUid);
		
			
			log.info("\n**********profileUid******************"+ profileUserUid);
			
			List<FollowingsResponseModel> followings = profileService.getAllFollowings(profileUserUid);
			
			return ResponseEntity.ok(followings);
			
	}
	
	
}














