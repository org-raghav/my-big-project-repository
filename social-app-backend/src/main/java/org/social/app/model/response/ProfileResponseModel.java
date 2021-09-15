package org.social.app.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponseModel {

	//user firstName is his profileName
	private String profileName;
	
	//userUid is his profileUid
	private String profileUid;
	
	//Is user is following the current profile(profileUid) which is displayed 
	@JsonProperty(namespace = "isFollowing")
	private boolean isFollowing = false;
	
	//get All counts of the user
	private ProfileCountsResponseModel counts;
	
	public boolean getIsFollowing() {
	    return isFollowing;
	}

	public void setIsFollowing(boolean isFollowing) {
	    this.isFollowing = isFollowing;
	}
}
