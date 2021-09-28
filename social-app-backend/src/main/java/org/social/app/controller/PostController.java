package org.social.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Post;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.securityConfigurations.UserPrincipal;
import org.social.app.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<PostResponseModel> createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, 
			@RequestBody CreatePostRequestModel createPostRequestModel) {
		
		String userUid = userPrincipal.getUserUid();
		
		log.info("User is authorized userUid  is::  {}", userUid);

		Post post = postService.createPost(createPostRequestModel, userUid);
		
		PostResponseModel payload = new PostResponseModel();
		BeanUtils.copyProperties(post, payload);
		
		payload.setPostId(post.getPostUid());
		payload.setCreatedBy(userUid);
		
		return ResponseEntity.created(null).body(payload);
	}
	
	@GetMapping(path = "/{postUid}")
	public ResponseEntity<PostResponseModel> getPost(@AuthenticationPrincipal UserPrincipal userPrincipal, 
			@PathVariable("postUid") String postUid){
		
		PostResponseModel payload = postService.getPostByPostUid(postUid);
		
		return ResponseEntity.ok(payload);
	}
	
	
}













