package org.social.app.model.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class PostResponseModel {
	
	private String postId;
	private String title;
	private String body;
	private LocalDateTime createdDate;
	private String createdBy;//postedBy in Future
	private String creatorName;

}
