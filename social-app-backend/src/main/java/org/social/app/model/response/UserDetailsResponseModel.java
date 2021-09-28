package org.social.app.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class UserDetailsResponseModel {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
		
}