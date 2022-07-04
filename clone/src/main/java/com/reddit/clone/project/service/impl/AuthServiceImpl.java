package com.reddit.clone.project.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.dto.AuthenticationResponse;
import com.reddit.clone.project.dto.LoginRequest;
import com.reddit.clone.project.dto.RegisterRequest;
import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.NotificationEmail;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.model.VerificationToken;
import com.reddit.clone.project.repository.UserRepository;
import com.reddit.clone.project.repository.VerificationTokenRepository;
import com.reddit.clone.project.service.AuthService;
import com.reddit.clone.project.service.MailService;
import com.reddit.clone.project.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
//@AllArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
//	private final 
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
    
	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	MailContentBuilder mailContentBuilder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	public void signup(RegisterRequest registerRequest) throws SpringRedditException{
		if (validateRequest(registerRequest)) {
			User user = new User();
			user.setUserName(registerRequest.getUserName());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			user.setEmail(registerRequest.getEmail());
			user.setCreated(Instant.now());
			user.setEnabled(false);
			userRepository.save(user);
			String token = generateVerificationToken(user);
			String message = mailContentBuilder.build(
					"Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
							+ Constants.ACTIVATION_EMAIL + "/" + token);
			log.info(message);
			log.info("token:{}", token);
			mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
			log.info("Email Sent successfully");
		} else {
			throw new SpringRedditException("User is already registered");
		}
	}

	private boolean validateRequest(RegisterRequest registerRequest) {
		Optional<User> existingUser=userRepository.findByEmail(registerRequest.getEmail());
		return (!existingUser.isPresent());
	
}

	private String generateVerificationToken(User user) {
		String token=UUID.randomUUID().toString();
		 VerificationToken verificationToken = new VerificationToken();
	        verificationToken.setToken(token);
	        verificationToken.setUser(user);
	        verificationTokenRepository.save(verificationToken);
	        return token;
	}

	@Override
	public void verifyAccount(String token) throws SpringRedditException {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

	private void fetchUserAndEnable(VerificationToken verificationToken) throws SpringRedditException {
		  String username = verificationToken.getUser().getUserName();
	        User user = userRepository.findByUserName(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
	        user.setEnabled(true);
		
	}

	@Override
	public AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException {
		 Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
	                loginRequest.getPassword()));
		 log.info("AUthentication:{}",authenticate.toString());
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        String authenticationToken = jwtProvider.generateToken(authenticate);
	        return new AuthenticationResponse(authenticationToken, loginRequest.getUserName());
	  
	}

	@Override
	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal=
				(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUserName(principal.getUsername()).
				orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}
	
	
}
