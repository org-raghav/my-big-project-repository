package org.social.app.controller;

import java.net.URI;

import org.social.app.entity.PostEntity;
import org.social.app.entity.UserEntity;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.model.response.SinglePostResponseModel;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<SinglePostResponseModel> createPost(@AuthenticationPrincipal UserPrincipal user, @RequestBody CreatePostRequestModel createPostRequestModel) {
		
		System.out.print("***I am *******" + user.getUserId());
		
		PostEntity postEntity = postService.createPost(user.getUserId(), createPostRequestModel);
		
		SinglePostResponseModel singlePostResponseModel = new SinglePostResponseModel();
		BeanUtils.copyProperties(postEntity, singlePostResponseModel);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}")
				.buildAndExpand(postEntity.getPostId()).toUri();

		return ResponseEntity.created(location).body(singlePostResponseModel);
		
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<SinglePostResponseModel> getPost(@PathVariable String postId) {
		PostEntity postEntity = postService.getSinglePost(postId);
		SinglePostResponseModel singlePostResponseModel = new SinglePostResponseModel();
		BeanUtils.copyProperties(postEntity, singlePostResponseModel);
		return ResponseEntity.ok(singlePostResponseModel);
	}
}
