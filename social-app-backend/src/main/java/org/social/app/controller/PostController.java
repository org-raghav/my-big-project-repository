package org.social.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Post;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.repository.PostRepository;
import org.social.app.repository.UserRepository;
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
	

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<PostResponseModel> createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreatePostRequestModel createPostRequestModel) {
		
		String userUid = userPrincipal.getUserUid();
		
		log.info("User is authorized userUid  is::  {}", userUid);

		Post post = postService.createPost(createPostRequestModel, userUid);
		
		PostResponseModel payload = new PostResponseModel();
		BeanUtils.copyProperties(post, payload);
		
		payload.setCreatedBy(userUid);
		
		return ResponseEntity.ok(payload);
	}
	
	@GetMapping(path = "/test1/{userId}")
	public List<Post> test1(@PathVariable Long userId) {
		List<Post> posts = postRepository.sqlFindAllPostByUserId(userId);
		System.out.println("*********" + posts);
		return  posts;
	}

	@GetMapping(path = "/test2/{userId}")
	public long test2(@PathVariable Long userId) {
		Long postCount = postRepository.sqlCountPostByUserId(userId);
		return postCount;
		
	}
	
	@GetMapping(path = "/test3/{userId}")
	public List<Post> test3(@PathVariable Long userId) {
		List<Post> posts = postRepository.findByUserId(userId);
		return posts;
		
	}
	
	
	
	
	
	
	
	
	
}













