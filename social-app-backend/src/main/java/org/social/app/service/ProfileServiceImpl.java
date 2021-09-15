package org.social.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.User;
import org.social.app.model.response.ProfileCountsResponseModel;
import org.social.app.model.response.ProfileResponseModel;
import org.social.app.repository.FollowRepository;
import org.social.app.repository.PostRepository;
import org.social.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ProfileServiceImpl implements ProfileService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ProfileResponseModel getProfile(String loggedInUserUid, String profileUserUid) {

		boolean isFollowing = false;

		User loggedInUser = userRepository.findByUserUid(loggedInUserUid);
		User profileUser = userRepository.findByUserUid(profileUserUid);

		ProfileResponseModel profileResponseModel = new ProfileResponseModel();
		profileResponseModel.setProfileName(profileUser.getFirstName() + " " + profileUser.getLastName());
		profileResponseModel.setProfileUid(profileUser.getUserUid());

		//No need to check as always by default false
		//condition 1::both user is same 
		//condition 2::both user are different but not follow each other
		//if (loggedInUserUid.equals(profileUserUid))
		//	isFollowing = false;
			
		
		if (followRepository.sqlIsFollowing(loggedInUser.getId(), profileUser.getId()))
			isFollowing = true;
		
		//Boolean db_isFollowing = followRepository.sqlIsFollowing(loggedInUser.getId(), profileUser.getId());
		//log.info("***** Is loggedInUser is following ProfileUser :: " + db_isFollowing);
		
		//Long db_isFollowingCount = followRepository.sqlIsFollowingCount(loggedInUser.getId(), profileUser.getId());
		//log.info("**** Count loggedInUser is following ProfileUser :: " + db_isFollowingCount);

		
		profileResponseModel.setIsFollowing(isFollowing);

		Long followerCount = followRepository.sqlFollowingCount(profileUser.getId());
		Long followingCount = followRepository.sqlFollowerCount(profileUser.getId());

		ProfileCountsResponseModel counts = new ProfileCountsResponseModel();
		counts.setFollowerCount(followerCount);
		counts.setFollowingCount(followingCount);

		Long postCount = postRepository.sqlCountPostByUserId(profileUser.getId());

		counts.setPostCount(postCount);

		profileResponseModel.setCounts(counts);
		return profileResponseModel;
	}

}
