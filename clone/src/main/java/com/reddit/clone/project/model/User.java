package com.reddit.clone.project.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
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
	
	private boolean enabled;
	
}
