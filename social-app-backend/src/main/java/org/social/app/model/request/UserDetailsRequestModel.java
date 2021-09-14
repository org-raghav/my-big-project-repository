package org.social.app.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class UserDetailsRequestModel {

	@Size(min = 1, max = 50, message = "first name must be between 1 to 50 character long only!")
	private String firstName;

	private String lastName;

	@Email(message = "Invalid Email!")
	@NotEmpty(message = "Email must not be empty")
	private String email;

	@Size(min = 3, max = 32, message = "password name must be between 1 to 50 character long only!")
	private String password;

		
}
