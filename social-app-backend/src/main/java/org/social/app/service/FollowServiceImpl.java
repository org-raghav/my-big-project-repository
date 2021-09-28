package org.social.app.service;

import org.social.app.entity.Follow;
import org.social.app.entity.FollowCompositeKey;
import org.social.app.entity.User;
import org.social.app.repository.FollowRepository;
import org.social.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class FollowServiceImpl implements FollowService {
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Follow addFollowing(String followerUid, String followingUid) {
		
		System.out.println("\n****************" + followingUid);
		System.out.println("\n****************" + followingUid);

		
		User follower = userRepository.findByUserUid(followerUid);
		User following = userRepository.findByUserUid(followingUid);
		
		System.out.println("\n****************"+ follower.getId());
		System.out.println("\n****************"+ following.getId());
		
		FollowCompositeKey key = new FollowCompositeKey();
		key.setFollowerId(follower.getId());
		key.setFollowingId(following.getId());
				
		Follow follow = new Follow();
		follow.setId(key);
		
		Follow savedFollow = followRepository.saveAndFlush(follow);
		System.out.println("\n**********************************************************");
		System.out.println("\n************************"+ savedFollow.getId().getFollowerId());
		System.out.println("\n************************"+ savedFollow.getId().getFollowingId());
		System.out.println("\n************************"+ savedFollow.getFollowingTimestamp());
		
		return savedFollow;
	}

	@Override
	public Follow removeFollowing(String followerUid, String followingUid) {
		
		System.out.println("\n****************" + followingUid);
		System.out.println("\n****************" + followingUid);

		
		User follower = userRepository.findByUserUid(followerUid);
		User following = userRepository.findByUserUid(followingUid);
		
		System.out.println("\n****************"+ follower.getId());
		System.out.println("\n****************"+ following.getId());
		
		
		followRepository.sqlRemoveFollowing(follower.getId(), following.getId());
		
		System.out.println("Remove done now stop following");
		
		return null;
		
	}
	

}
