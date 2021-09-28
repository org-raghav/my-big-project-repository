package org.social.app.model.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class FollowResponseModel {
	
	private String followerId;
	private String followingId;
	private boolean isFollowing;

	public boolean getIsFollowing() {
		return isFollowing;
	}

	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	
	
	

}
