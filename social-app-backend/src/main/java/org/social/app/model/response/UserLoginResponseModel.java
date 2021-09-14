package org.social.app.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties //advised by stackoverflow
@Getter@Setter@NoArgsConstructor
public class UserLoginResponseModel {

	private String firstName;
	private String lastName;
	private String userUid;

}
