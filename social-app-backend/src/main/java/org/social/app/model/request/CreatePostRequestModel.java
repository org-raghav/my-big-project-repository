package org.social.app.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class CreatePostRequestModel {

	private String title;
	private String body;
}
