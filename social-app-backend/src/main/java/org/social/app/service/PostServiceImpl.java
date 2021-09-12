package org.social.app.service;

import java.util.Optional;
import java.util.UUID;

import org.social.app.entity.PostEntity;
import org.social.app.entity.UserEntity;
import org.social.app.exception.PostNotFoundException;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.repository.PostRepository;
import org.social.app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository  userRepository;

	@Override
	public PostEntity createPost(String userId, CreatePostRequestModel createPostRequestModel) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		PostEntity postEntity = new PostEntity();
		BeanUtils.copyProperties(createPostRequestModel, postEntity);
		postEntity.setPostId(UUID.randomUUID().toString());
		postEntity.setUserEntity(userEntity);
		PostEntity savedPostEntity = postRepository.saveAndFlush(postEntity);
		return savedPostEntity;
	}
	
	@Override
	public PostEntity getSinglePost(String postId) {
		Optional<PostEntity> optional = postRepository.findByPostId(postId);
		if(optional.isPresent())
			return optional.get();
		else throw new PostNotFoundException("No Such Post is Found !" + postId);
	}

}
