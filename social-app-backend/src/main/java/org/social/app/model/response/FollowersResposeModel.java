package org.social.app.model.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class FollowersResposeModel {
	
	private String profileId;
	private String firstName;
	private  String lastName;
	private LocalDateTime follwingTimestamp;
	

}
