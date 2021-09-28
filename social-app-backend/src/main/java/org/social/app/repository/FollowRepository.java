package org.social.app.repository;


import java.util.List;

import org.social.app.entity.Follow;
import org.social.app.entity.FollowCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowCompositeKey> {
	

	// count total following of a follower
	@Query(value = "SELECT COUNT(following_id) FROM follow WHERE follower_id=:followerId", nativeQuery = true)
	public Long sqlFollowerCount(@Param("followerId") Long followerId);

	// count total follower of a loggedInUser
	@Query(value = "SELECT COUNT(follower_id) FROM follow WHERE following_id=:followingId", nativeQuery = true)
	public Long sqlFollowingCount(@Param("followingId") Long followingId);
	
	// count must be 1 if follower is following neither 1
	//@Query(value = "SELECT COUNT(*) FROM follow WHERE follower_id=:followerId AND following_id=:followingId LIMIT 1", nativeQuery = true)
	//public Long sqlIsFollowingCount(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

	// @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.name = :companyName")
	// aggregate functions such as COUNT, AVG, SUM, etc., unless they are used in
	// combination with GROUP BY.

	//table name in small letters
	//sql query keywords capital letter
	@Query(value = "SELECT CASE WHEN COUNT(*)>0 THEN 'true' ELSE 'false' END FROM follow WHERE follower_id=:followerId AND following_id=:followingId", nativeQuery = true)
	public Boolean sqlIsFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);
	
	@Modifying
	@Query(value = "DELETE FROM follow WHERE follower_id=:followerId AND following_id=:followingId", nativeQuery = true)
	public void sqlRemoveFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);
	
	//select all followers
	@Query(value = "SELECT * FROM follow WHERE following_id=:userId", nativeQuery = true)
	public List<Follow> sqlgetAllFollowers(@Param("userId") Long userId);
	
	//select all followings
	@Query(value = "SELECT * FROM follow WHERE follower_id=:userId", nativeQuery = true)
	public List<Follow> sqlgetAllFollowings(@Param("userId") Long userId);
		


}
