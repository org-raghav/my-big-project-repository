package org.social.app.repository;

import java.util.List;
import java.util.Optional;

import org.social.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * 
	 * Spring Data JPA Query
	 * 
	 * Method Name matters
	 * 
	 * query done by method name
	 * 
	 */

	// select a post by id
	Optional<Post> findById(Long id);

	// select a post by postUid/post_uid
	Optional<Post> findByPostUid(String postUid);

	// select all post by user_id //tested OK
	List<Post> findByUserId(Long userId);

	/**
	 * 
	 * Native SQL Query
	 * 
	 */

	// select a post by post id //In database it is id field
	@Query(value = "SELECT * FROM post WHERE id=:id", nativeQuery = true)
	Optional<Post> sqlFindPostById(Long id);

	// select a post by post_uid //In database it is post_uid field
	@Query(value = "SELECT * FROM post WHERE post_uid=:postUid", nativeQuery = true)
	Optional<Post> sqlFindPostByPostUid(Long postUid);

	// select all post by user_id //Tested OK
	@Query(value = "SELECT * FROM post WHERE user_id=:userId", nativeQuery = true)
	List<Post> sqlFindAllPostByUserId(@Param("userId") Long userId);

	// count all post by a user_id //Tested OK
	@Query(value = "SELECT  COUNT(post_uid) FROM post WHERE user_id=:userId", nativeQuery = true)
	Long sqlCountPostByUserId(@Param("userId") Long userId);

}
