package org.social.app.service;

import org.social.app.entity.PostEntity;
import org.social.app.model.request.CreatePostRequestModel;

public interface PostService {
	
	public PostEntity createPost(String userId, CreatePostRequestModel createPostRequestModel);
	
	public PostEntity getSinglePost(String postId); 

}
