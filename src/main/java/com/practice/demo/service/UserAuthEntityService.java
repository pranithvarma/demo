package com.practice.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.demo.entity.UserAuthEntity;
import com.practice.demo.repo.UserAuthEntityRepository;

@Service
public class UserAuthEntityService implements UserDetailsService{

	private UserAuthEntityRepository repo;
	public UserAuthEntityService(UserAuthEntityRepository repo) {
		super();
		this.repo = repo;
	}
	
	public UserDetails save(UserAuthEntity userauth) {
		return repo.save(userauth);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}

}
