package org.social.app.repository;

import java.util.Optional;

import org.social.app.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
	
	Optional<PostEntity> findByPostId(String postId);

}
