package com.reddit.clone.project.service;

import com.reddit.clone.project.dto.AuthenticationResponse;
import com.reddit.clone.project.dto.LoginRequest;
import com.reddit.clone.project.dto.RegisterRequest;
import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.User;

public interface AuthService {

	void signup(RegisterRequest registerRequest) throws SpringRedditException;

	void verifyAccount(String token) throws SpringRedditException;

	AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException;

	User getCurrentUser();
}
