package com.bridgelabz.service;

import com.bridgelabz.model.LoginRequest;
import com.bridgelabz.model.Register;
import com.bridgelabz.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RegisterRepository repository;

    public String registerUser(Register registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
            throw new IllegalArgumentException("Data required");
        }

        boolean isUsername = repository.existsByUsername(registerRequest.getUsername());

        if (isUsername) {
            throw new RuntimeException("User already exists");
        }

        // Save user details
        repository.save(registerRequest);
        return "User registered successfully.";
    }

    public Optional<Register> userLogin(LoginRequest loginRequest) {
        Optional<Register> userLogin = repository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        if (userLogin.isPresent()) {
            return userLogin;
        } else {
            return null;
        }
    }
}