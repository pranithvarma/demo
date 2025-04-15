package com.practice.demo.service;

import com.practice.demo.authentication.LoginUserDto;
import com.practice.demo.authentication.RegisterUserDto;
import com.practice.demo.entity.UserAuthEntity;
import com.practice.demo.repo.UserAuthEntityRepository;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserAuthEntityRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserAuthEntityRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAuthEntity signup(RegisterUserDto input) {
        UserAuthEntity user = new UserAuthEntity()
                .setUsername(input.username())
                .setPassword(passwordEncoder.encode(input.password()));

        return userRepository.save(user);
    }

    public UserAuthEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return (UserAuthEntity) Optional.of(userRepository.findByUsername(input.username()))
                .orElseThrow();
    }
}
