package org.social.app.exception;

public class PostNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 901378103344052737L;
	
	public PostNotFoundException(String message) {
		super(message);
	}

}
