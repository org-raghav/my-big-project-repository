package org.social.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.model.request.SearchRequestModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.securityConfigurations.UserPrincipal;
import org.social.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/search")
public class SerachController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostService postService;

	int count = 0;

	@PostMapping
	public ResponseEntity<List<PostResponseModel>> searchPosts(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@RequestBody @Valid SearchRequestModel searchRequestModel) {

		String userUid = userPrincipal.getUserUid();
		
		System.out.println("*****userUid******" + userUid);

		log.info("***********User is authorized userUid  is::  {}", userUid);

		String searchTerm = searchRequestModel.getSearchTerm();
		System.out.println("*****searchTearm******" + searchTerm);
		System.out.println("*******value of count is*****" + count++);
		List<PostResponseModel> payload = postService.getAllPostBySearch(searchTerm);
		//payload.forEach(System.out::println);
		return ResponseEntity.ok(payload);
	}

}
