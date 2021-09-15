package org.social.app.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class PostResponseModel {
	
	private String postUid;
	private String title;
	private String body;
	private String createdBy;

}
