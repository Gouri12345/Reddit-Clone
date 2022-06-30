package com.reddit.clone.project.service.impl;

import com.reddit.clone.project.model.RegisterRequest;

public interface AuthService {

	void signup(RegisterRequest registerRequest);

	void verifyAccount(String token);
}
