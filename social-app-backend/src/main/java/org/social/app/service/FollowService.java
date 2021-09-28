package org.social.app.service;

import org.social.app.entity.Follow;

public interface FollowService {

	public Follow addFollowing(String follwerUid, String followingUid);
	
	public Follow removeFollowing(String follwerUid, String followingUid);
}
