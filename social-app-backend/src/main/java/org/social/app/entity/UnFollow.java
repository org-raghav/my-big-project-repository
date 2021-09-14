package org.social.app.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter@NoArgsConstructor
public class UnFollow {

	
	@CreationTimestamp
	private LocalDateTime unFollowingTimestamp;
	
			
}
