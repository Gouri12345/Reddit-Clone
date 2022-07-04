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

import com.reddit.clone.project.dto.AuthenticationResponse;
import com.reddit.clone.project.dto.LoginRequest;
import com.reddit.clone.project.dto.RegisterRequest;
import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.service.AuthService;

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
		
		try {
		 authService.signup(registerRequest);
//	        return new ResponseEntity<String>("User registration successful",HttpStatus.OK);}
		return ResponseEntity.ok("User registration Successful");
		}
		 catch(SpringRedditException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws SpringRedditException{
		 return authService.login(loginRequest);
		
	}
	
	 @GetMapping("accountVerification/{token}")
	    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringRedditException {
	        authService.verifyAccount(token);
	        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
	    }
	
}
