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
public class UnFollow implements Serializable{

	private static final long serialVersionUID = -4001761078689241764L;
	
	@EmbeddedId
	private UnFollowCompositeKey id;
	
	@CreationTimestamp
	private LocalDateTime unFollowingTimestamp;
			
}
