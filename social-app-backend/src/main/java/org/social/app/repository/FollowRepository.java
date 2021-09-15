package org.social.app.repository;

import org.social.app.entity.Follow;
import org.social.app.entity.FollowCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowCompositeKey> {

	// count total following of a follower
	@Query(value = "SELECT COUNT(following_id) FROM FOLLOW WHERE follower_id=:followerId", nativeQuery = true)
	public Long sqlFollowingCount(@Param("followerId") Long followerId);

	// count total follower of a loggedInUser
	@Query(value = "SELECT COUNT(follower_id) FROM FOLLOW WHERE following_id=:followingId", nativeQuery = true)
	public Long sqlFollowerCount(@Param("followingId") Long followingId);

	// count must be 1 if follower is following neither 1
	@Query(value = "SELECT COUNT(*) FROM FOLLOW WHERE follower_id=:followerId AND following_id=:followingId LIMIT 1", nativeQuery = true)
	public Long sqlIsFollowingCount(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

	// @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.name = :companyName")
	// aggregate functions such as COUNT, AVG, SUM, etc., unless they are used in
	// combination with GROUP BY.

	@Query(value = "SELECT CASE WHEN COUNT(*)>0 THEN true ELSE false END FROM FOLLOW f WHERE f.follower_id=:followerId AND f.following_id=:followingId", nativeQuery = true)
	public Boolean sqlIsFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

}
