package org.social.app.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter@Setter@NoArgsConstructor
public class FollowCompositeKey implements Serializable {

	private static final long serialVersionUID = 6719431041831727856L;

	//contains the follower userId
	private User Follower;

	//contains following userId
	private String following;
		
}
