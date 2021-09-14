package org.social.app.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class ProfileResponseModel {

	//user firstName is his profileName
	private String profileName;
	
	//userUid is his profileUid
	private String profileUid;
	
	//Is user is following the current profile(profileUid) which is displayed 
	private boolean isFollowing = false;
	
	//get All counts of the user
	private ProfileCountsResponseModel profileCountsResponseModel;
}
