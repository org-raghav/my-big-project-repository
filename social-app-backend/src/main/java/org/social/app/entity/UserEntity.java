package org.social.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 5313493413859894403L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String firstName;

	@Column
	private String lastName;

	@Column(nullable = false, unique = true, updatable = true, length = 120)
	private String email;

	private String encryptedPassword;

	private String userId;

	private String emailVerificationToken;

	@Column(nullable = false)
	private boolean emailVerificationStatus = false;

	
	public UserEntity() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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


	public String getEncryptedPassword() {
		return encryptedPassword;
	}


	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}


	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}


	public boolean isEmailVerificationStatus() {
		return emailVerificationStatus;
	}


	public void setEmailVerificationStatus(boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}
	

}
