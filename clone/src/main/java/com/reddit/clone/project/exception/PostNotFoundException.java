package com.reddit.clone.project.exception;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String message) {
        super(message);
    }
}
