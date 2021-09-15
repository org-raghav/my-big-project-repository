package org.social.app.service;

import org.social.app.model.response.ProfileResponseModel;

public interface ProfileService {

	public ProfileResponseModel getProfile(String loggedInUserUid, String profileUserUid);
}
