package org.social.app.repository;

import org.social.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);

	boolean existsByUserId(String userId);

	UserEntity findByUserId(String userId);

	UserEntity findByEmail(String email);

	UserEntity findByEmailVerificationToken(String token);

	void deleteByUserId(String userId);

}
