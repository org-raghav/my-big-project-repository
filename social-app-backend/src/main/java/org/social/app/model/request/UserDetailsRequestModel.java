package org.social.app.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDetailsRequestModel {

	@Size(min = 1, max = 50, message = "first name must be between 1 to 50 character long only!")
	private String firstName;

	private String lastName;

	@Email(message = "Invalid Email!")
	@NotEmpty(message = "Email must not be empty")
	private String email;

	@Size(min = 3, max = 32, message = "password name must be between 1 to 50 character long only!")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
