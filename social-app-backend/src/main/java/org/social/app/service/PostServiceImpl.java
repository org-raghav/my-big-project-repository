package org.social.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Post;
import org.social.app.entity.User;
import org.social.app.exception.UserNotFoundException;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.repository.PostRepository;
import org.social.app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Post createPost(CreatePostRequestModel postRequestModel, String userUid) {

		User user = userRepository.findByUserUid(userUid);

		if (user == null)
			throw new UserNotFoundException("USER NOT FOUND:: " + userUid);

		Post post = new Post();
		BeanUtils.copyProperties(postRequestModel, post);

		post.setPostUid(UUID.randomUUID().toString());
		post.setUser(user);
		//post.setCreatedDate(LocalDateTime.now());
		
		Post postEntity = postRepository.saveAndFlush(post);

		log.info("your post is successfully posted! and database post_id is:: {}", postEntity.getId());

		return postEntity;

	}

	@Override
	public List<Post> getAllPostByUserId(String userUid) {
		
		User user = userRepository.findByUserUid(userUid);
		
		if (user == null)
			throw new UserNotFoundException("USER NOT FOUND:: " + userUid);

		return postRepository.sqlFindAllPostByUserId(user.getId());
	}
	

	@Override
	public PostResponseModel getPostByPostUid(String postUid) {
		Optional<Post> optional = postRepository.sqlFindPostByPostUid(postUid);
		if(optional.isPresent()) {
			Post post = optional.get();
			PostResponseModel postResponseModel = new PostResponseModel();
			BeanUtils.copyProperties(post, postResponseModel);
			postResponseModel.setCreatedBy(post.getUser().getUserUid());
			postResponseModel.setCreatorName(post.getUser().getFirstName());
			postResponseModel.setPostId(postUid);
			post.getUser().getUserUid();
			
			return postResponseModel;
		}else {
			throw new RuntimeException("No Such post Found in DB " + postUid);
		}
	}

	@Override
	public List<PostResponseModel> getAllPostBySearch(String searchTearm) {
		System.out.println("I am serIMP serchterm is::" + searchTearm);
		List<Post> posts = postRepository.sqlFindAllPostByFullTextSearch(searchTearm);
		//posts.forEach(System.out::println);
		List<PostResponseModel> searchResult  =  new  ArrayList<>();
		posts.forEach(post -> {
			PostResponseModel postResponseModel = new PostResponseModel();
			postResponseModel.setPostId(post.getPostUid());
			postResponseModel.setBody(post.getBody());
			postResponseModel.setTitle(post.getTitle());
			postResponseModel.setCreatedDate(post.getCreatedDate());
			postResponseModel.setCreatedBy(post.getUser().getFirstName()+  " "  + post.getUser().getLastName());
			searchResult.add(postResponseModel);
		});
		return searchResult;
	}
	
	
}
