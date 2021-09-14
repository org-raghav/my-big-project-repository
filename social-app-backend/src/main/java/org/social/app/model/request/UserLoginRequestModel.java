package org.social.app.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class UserLoginRequestModel {
	
	private String email;
	private String password;
	
}
