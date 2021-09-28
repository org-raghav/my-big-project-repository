package org.social.app.service;

import java.util.List;

import org.social.app.model.response.FollowersResposeModel;
import org.social.app.model.response.FollowingsResponseModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.model.response.ProfileResponseModel;

public interface ProfileService {

	public ProfileResponseModel getProfile(String loggedInUserUid, String profileUserUid);
	
	public List<PostResponseModel> getAllPostByProfileUserUid(String profileUserUid);
	
	public PostResponseModel getPostByPostUid(String postUid);
	
	public List<FollowersResposeModel> getAllFollowers(String profileUid);
	
	public List<FollowingsResponseModel> getAllFollowings(String profileUid);
	
}
