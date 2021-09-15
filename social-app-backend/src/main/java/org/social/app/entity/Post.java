package org.social.app.entity;

import java.io.Serializable;

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
public class Post implements Serializable{

	private static final long serialVersionUID = 7488903407258825040L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String body;
	
	@Column(nullable = false)
	private String postUid;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private User user;
	
}
