package org.social.app.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Post;
import org.social.app.entity.User;
import org.social.app.exception.UserNotFoundException;
import org.social.app.model.request.CreatePostRequestModel;
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

	public Post createPost(CreatePostRequestModel postRequestModel, String userUid) {

		User user = userRepository.findByUserUid(userUid);

		if (user == null)
			throw new UserNotFoundException("USER NOT FOUND:: " + userUid);

		Post post = new Post();
		BeanUtils.copyProperties(postRequestModel, post);

		post.setPostUid(UUID.randomUUID().toString());
		post.setUser(user);

		Post postEntity = postRepository.saveAndFlush(post);

		log.info("your post is successfully posted! and database post_id is:: {}", postEntity.getId());

		return postEntity;

	}

	@Override
	public List<Post> getAllPostByUserId(String userId) {
		return null;
	}
	
}
