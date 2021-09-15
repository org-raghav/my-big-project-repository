package org.social.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter@Setter@NoArgsConstructor
public class User implements Serializable {

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

	private String userUid;

	private String emailVerificationToken;

	@Column(nullable = false)
	private boolean emailVerificationStatus = false;

	@OneToMany(mappedBy = "user")
	private List<Post> posts;
}
