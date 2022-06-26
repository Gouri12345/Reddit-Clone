package com.reddit.clone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;
	
	@NotNull(message="User name cannot be blank")
	private String userName;
	
	@NotNull(message="Password cannot be blank")
	private String password;
	
	@Email
	@NotEmpty(message="Email cannot be blank")
	private String email;
	
	private Instant created;
	
	private boolean isEnabled;
	
}
