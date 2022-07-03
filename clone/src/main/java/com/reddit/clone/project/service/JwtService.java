package com.reddit.clone.project.service;

import org.springframework.security.core.Authentication;

import com.reddit.clone.project.exception.SpringRedditException;

public interface JwtService {
	 public String generateToken(Authentication authentication) throws SpringRedditException;
}
