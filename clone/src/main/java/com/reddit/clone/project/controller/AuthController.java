package com.reddit.clone.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.model.RegisterRequest;
import com.reddit.clone.project.service.impl.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
//@AllArgsConstructor
@Slf4j
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SpringRedditException{
		log.info(registerRequest.toString());
		 authService.signup(registerRequest);
	        return new ResponseEntity<String>("User registration successful",HttpStatus.OK);
		
	}
	
	 @GetMapping("accountVerification/{token}")
	    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
	        authService.verifyAccount(token);
	        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
	    }
	
}
