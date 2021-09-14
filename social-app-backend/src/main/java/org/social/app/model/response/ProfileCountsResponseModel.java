package org.social.app.model.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class ProfileCountsResponseModel {

	private Long postCount;
	private Long followerCount;
	private Long followingCount;
	
}
