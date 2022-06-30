package com.reddit.clone.project.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.NotificationEmail;
import com.reddit.clone.project.model.RegisterRequest;
import com.reddit.clone.project.model.User;
import com.reddit.clone.project.model.VerificationToken;
import com.reddit.clone.project.repository.UserRepository;
import com.reddit.clone.project.repository.VerificationTokenRepository;
import com.reddit.clone.project.util.Constants;

import lombok.AllArgsConstructor;
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
//	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user=new User();
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token =generateVerificationToken(user);
		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + Constants.ACTIVATION_EMAIL + "/" + token);
		log.info(message);
		log.info("token:{}",token);
        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
  
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
	public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

//	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		  String username = verificationToken.getUser().getUserName();
	        User user = userRepository.findByUserName(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
	        user.setEnabled(true);
//	        userRepository.save(user);
		
	}
	
	
}
