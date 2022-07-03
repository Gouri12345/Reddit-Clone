package com.reddit.clone.project.service;

import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.AuthenticationResponse;
import com.reddit.clone.project.model.LoginRequest;
import com.reddit.clone.project.model.RegisterRequest;

public interface AuthService {

	void signup(RegisterRequest registerRequest) throws SpringRedditException;

	void verifyAccount(String token) throws SpringRedditException;

	AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException;
}
