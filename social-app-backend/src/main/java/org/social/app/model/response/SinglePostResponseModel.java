package org.social.app.model.response;

public class SinglePostResponseModel {

	private String postId;
	private String title;
	private String body;
	
	public SinglePostResponseModel() {}

	public SinglePostResponseModel(String postId, String title, String body) {
		this.postId = postId;
		this.title = title;
		this.body = body;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
