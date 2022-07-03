package com.reddit.clone.project.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.project.model.User;
import com.reddit.clone.project.repository.UserRepository;
import static java.util.Collections.singletonList;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{
		   Optional<User> userOptional = userRepository.findByUserName(username);
	        User user = userOptional
	                .orElseThrow(() -> new UsernameNotFoundException("No user " +
	                        "Found with username : " + username));

	        return new org.springframework.security
	                .core.userdetails.User(user.getUserName(), user.getPassword(),
	                user.isEnabled(), true, true,
	                true, getAuthorities("USER"));
	}
	 private Collection<? extends GrantedAuthority> getAuthorities(String role) {
	        return singletonList(new SimpleGrantedAuthority(role));
	    }
	
}
