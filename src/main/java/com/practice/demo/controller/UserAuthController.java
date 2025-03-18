package com.practice.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.entity.UserAuthEntity;
import com.practice.demo.service.UserAuthEntityService;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
	
	private UserAuthEntityService service;
	private  PasswordEncoder passwordEncoder;
	
	
	
	public UserAuthController(UserAuthEntityService service, PasswordEncoder passwordEncoder) {
		super();
		this.service = service;
		this.passwordEncoder = passwordEncoder;
	}


	@PostMapping("/register")
	ResponseEntity<UserDetails> registerUser(@RequestBody UserAuthEntity userauth){
	
		userauth.setPassword(passwordEncoder.encode(userauth.getPassword()));
		return ResponseEntity.ok(service.save(userauth));
		
	}
	

}
