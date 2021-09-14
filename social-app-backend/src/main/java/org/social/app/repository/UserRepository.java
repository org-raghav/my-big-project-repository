package org.social.app.repository;

import org.social.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	boolean existsByUserUid(String userUid);

	User findByUserUid(String userUid);

	User findByEmail(String email);

	User findByEmailVerificationToken(String token);

	void deleteByUserUid(String userUid);

}
