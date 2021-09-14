package org.social.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter@Setter@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String body;
	
	@Column(nullable = false)
	private String postId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private User user;

	
}
