package org.social.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.app.entity.Follow;
import org.social.app.entity.Post;
import org.social.app.entity.User;
import org.social.app.model.response.FollowersResposeModel;
import org.social.app.model.response.FollowingsResponseModel;
import org.social.app.model.response.PostResponseModel;
import org.social.app.model.response.ProfileCountsResponseModel;
import org.social.app.model.response.ProfileResponseModel;
import org.social.app.repository.FollowRepository;
import org.social.app.repository.PostRepository;
import org.social.app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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

		Boolean isFollowing = false;
		
		System.out.println("*********isFollowing  is  :::******" + isFollowing);

		User loggedInUser = userRepository.findByUserUid(loggedInUserUid);
		User profileUser = userRepository.findByUserUid(profileUserUid);
		
		System.out.println("***profile service impl wah r dimpl****************\n" + profileUser.getFirstName());
		System.out.println("***profile service impl wah r dimpl****************\n" + loggedInUser.getFirstName());

		ProfileResponseModel profileResponseModel = new ProfileResponseModel();
		profileResponseModel.setProfileName(profileUser.getFirstName());
		profileResponseModel.setProfileId(profileUser.getUserUid());

		
		System.out.println("\n***********loggedInUser.getId()*********" + loggedInUser.getId());
		System.out.println("\n**********profileUser.getId()*************" + profileUser.getId());
		
		Boolean sqlIsFollowing = followRepository.sqlIsFollowing(loggedInUser.getId(), profileUser.getId());
		System.out.println("***********************sqlIsFollowing" + sqlIsFollowing);
		if (followRepository.sqlIsFollowing(loggedInUser.getId(), profileUser.getId()))
			isFollowing = true;
		
		//Boolean db_isFollowing = followRepository.sqlIsFollowing(loggedInUser.getId(), profileUser.getId());
		//log.info("***** Is loggedInUser is following ProfileUser :: " + db_isFollowing);
		
		//Long db_isFollowingCount = followRepository.sqlIsFollowingCount(loggedInUser.getId(), profileUser.getId());
		//log.info("**** Count loggedInUser is following ProfileUser :: " + db_isFollowingCount);

		System.out.println("****the value of isFollowing***********%%%%%%%******" + isFollowing);
		
		profileResponseModel.setIsFollowing(isFollowing);

		Long followerCount = followRepository.sqlFollowingCount(profileUser.getId());
		
		System.out.println("the number of follwer count  is:::" + followerCount);
		Long followingCount = followRepository.sqlFollowerCount(profileUser.getId());

		
		  ProfileCountsResponseModel counts = new ProfileCountsResponseModel();
		  counts.setFollowerCount(followerCount);
		  counts.setFollowingCount(followingCount);
		  
		  Long postCount = postRepository.sqlCountPostByUserId(profileUser.getId());
		  
		  log.info("Total post count is:: " + postCount);
		  
		  counts.setPostCount(postCount);
		  
		  profileResponseModel.setCounts(counts);
		 
		return profileResponseModel;
	}
	
	@Override
	public List<PostResponseModel> getAllPostByProfileUserUid(String profileUserUid){
		
		User user = userRepository.findByUserUid(profileUserUid);
		
		List<Post> posts = postRepository.sqlFindAllPostByUserId(user.getId());
		
		List<PostResponseModel> responseList = new ArrayList<>();
		
		for(int i=0; i<posts.size(); i++) {
			PostResponseModel postResponseModel = new PostResponseModel();
			BeanUtils.copyProperties(posts.get(i), postResponseModel);
			postResponseModel.setPostId(posts.get(i).getPostUid());
			postResponseModel.setCreatedBy(profileUserUid);
			responseList.add(postResponseModel);
		}
		
		return responseList;
	}

	@Override
	public PostResponseModel getPostByPostUid(String postUid) {
		Optional<Post> optional = postRepository.sqlFindPostByPostUid(postUid);
		if(optional.isPresent()) {
			Post post = optional.get();
			PostResponseModel postResponseModel = new PostResponseModel();
			BeanUtils.copyProperties(post, postResponseModel);
			return postResponseModel;
		}else {
			throw new RuntimeException("No Such post Found in DB " + postUid);
		}
	}
	
	@Override
	public List<FollowersResposeModel> getAllFollowers(String profileUid) {
		
		log.info("\n::%%%%%%%%%%%%%%%%% ProfileServiceImpl getFollowers() method executing...%%%%%%%%%%%%%%%%%%%::");
		
		User user = userRepository.findByUserUid(profileUid);
		
		log.info("\n:: %%%%%%%%%%%%%%%% getting user db id %%%%%%%%%%%%%%%%%%% " +  user.getId());
		
		List<Follow> followers = followRepository.sqlgetAllFollowers(user.getId());
		
		List<Long> followersIds = followers.stream().map((f) -> f.getId().getFollowerId()).collect(Collectors.toList());
		
		followersIds.forEach((id) -> log.info("\n %%%%%%%%%%%%%%%% user follwer id  in db %%%%%%%%%%%%%%%% ::" + id));
		
		List<User> users = userRepository.findAllById(followersIds);
		
		users.forEach((u) ->  {
			
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of follower::firstName:: %%%%%%%%%%%%%%%%" + u.getFirstName());
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of follower::lastName:: %%%%%%%%%%%%%%%%" + u.getLastName());
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of follower::FB joining Date:: %%%%%%%%%%%%%%%%" + u.getJoinningDate());
		});
		
		List<FollowersResposeModel> FollowersList = new ArrayList<>();
		
		//For Future i will implement it
		/*
		 * followers.forEach((f) -> {
		 * 
		 * FollowersResposeModel newFollower = new FollowersResposeModel();
		 * newFollower.setFollwingTimestamp(f.getFollowingTimestamp());
		 * newFollower.setProfileId(users.g);
		 * 
		 * });
		 */
		
		
		for(int i=0; i<followers.size(); i++) {
			FollowersResposeModel newFollower = new FollowersResposeModel();
			User u = users.get(i);
			Follow f = followers.get(i);
			if(u.getId() == f.getId().getFollowerId()) {
				log.info("\n  %%%%%%%%%%%%%%%% Both u and f  having the same id %%%%%%%%%%%%%%%% " + u.getId()  + " ::: " + f.getId().getFollowerId());
				newFollower.setFollwingTimestamp(f.getFollowingTimestamp());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollower timestamp:: %%%%%%%%%%%%%%%%" + f.getFollowingTimestamp());
				newFollower.setProfileId(u.getUserUid());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollower profileId:: %%%%%%%%%%%%%%%%" + u.getUserUid());
				newFollower.setFirstName(u.getFirstName());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollower firstName:: %%%%%%%%%%%%%%%%" + u.getFirstName());
				newFollower.setLastName(u.getLastName());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollower lastName:: %%%%%%%%%%%%%%%%" + u.getLastName());
				
				FollowersList.add(newFollower);
			}
			
		}
		
		
		return FollowersList;
	}
	
	@Override
	public List<FollowingsResponseModel> getAllFollowings(String profileUid) {
		
		log.info("\n::%%%%%%%%%%%%%%%%% ProfileServiceImpl getFollowers() method executing...%%%%%%%%%%%%%%%%%%%::");
		
		User user = userRepository.findByUserUid(profileUid);
		
		log.info("\n:: %%%%%%%%%%%%%%%% getting user db id %%%%%%%%%%%%%%%%%%% " +  user.getId());
		
		List<Follow> followings = followRepository.sqlgetAllFollowings(user.getId());
		
		List<Long> followingsIds = followings.stream().map((f) -> f.getId().getFollowingId()).collect(Collectors.toList());
		
		followingsIds.forEach((id) -> log.info("\n %%%%%%%%%%%%%%%% user follwing id  in db %%%%%%%%%%%%%%%% ::" + id));
		
		List<User> users = userRepository.findAllById(followingsIds);
		
		users.forEach((u) ->  {
			
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of following::firstName:: %%%%%%%%%%%%%%%%" + u.getFirstName());
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of following::lastName:: %%%%%%%%%%%%%%%%" + u.getLastName());
			log.info("\n  %%%%%%%%%%%%%%%% fetching data of following::FB joining Date:: %%%%%%%%%%%%%%%%" + u.getJoinningDate());
		});
		
		List<FollowingsResponseModel> FollowingsList = new ArrayList<>();
		
		
		for(int i=0; i<followings.size(); i++) {
			FollowingsResponseModel newFollowing = new FollowingsResponseModel();
			User u = users.get(i);
			Follow f = followings.get(i);
			if(u.getId() == f.getId().getFollowingId()) {
				log.info("\n  %%%%%%%%%%%%%%%% Both u and f  having the same id %%%%%%%%%%%%%%%% " + u.getId()  + " ::: " + f.getId().getFollowerId());
				newFollowing.setFollwingTimestamp(f.getFollowingTimestamp());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollowing timestamp:: %%%%%%%%%%%%%%%%" + f.getFollowingTimestamp());
				newFollowing.setProfileId(u.getUserUid());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollowing profileId:: %%%%%%%%%%%%%%%%" + u.getUserUid());
				newFollowing.setFirstName(u.getFirstName());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollowing firstName:: %%%%%%%%%%%%%%%%" + u.getFirstName());
				newFollowing.setLastName(u.getLastName());
				log.info("\n  %%%%%%%%%%%%%%%% setting newFollowing lastName:: %%%%%%%%%%%%%%%%" + u.getLastName());
				
				FollowingsList.add(newFollowing);
			}else {
				log.info("\n %%%%%%%%%%%%%%%%%%%%%%% No Following  overall %%%%%%%%%%%%%%%%%%%%%%");
			}
			
			
		}
		
	
		return FollowingsList;
		
	}
	
	
	

	
}
