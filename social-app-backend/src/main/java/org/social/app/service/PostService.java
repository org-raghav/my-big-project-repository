package org.social.app.service;

import java.util.List;

import org.social.app.entity.Post;
import org.social.app.model.request.CreatePostRequestModel;
import org.social.app.model.response.PostResponseModel;

public interface PostService {

	public Post createPost(CreatePostRequestModel postRequestModel, String userUid);
	
	public List<Post> getAllPostByUserId(String userId);
	
	public PostResponseModel getPostByPostUid(String postUid);
	
	public List<PostResponseModel> getAllPostBySearch(String searchTearm);
	

}
