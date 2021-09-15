package org.social.app.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter@Setter@NoArgsConstructor
public class Follow implements Serializable{
	
	private static final long serialVersionUID = -6469423474998275521L;

	@EmbeddedId
	private FollowCompositeKey id;
	
	@CreationTimestamp
	private LocalDateTime followingTimestamp;
			
}
