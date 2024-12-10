package com.bridgelabz.controller;

import com.bridgelabz.model.JwtResponse;
import com.bridgelabz.model.LoginRequest;
import com.bridgelabz.model.Register;
import com.bridgelabz.service.UserService;
import com.bridgelabz.utiliy.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegisterController {
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Register registerRequest) {
        try {
            String message = userService.registerUser(registerRequest);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed due to an unexpected error.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<Register> login = userService.userLogin(loginRequest);
        if (login.isPresent()) {
            return ResponseEntity.ok(new JwtResponse(tokenUtility.createToken(login.get().getUser_id(), login.get().getRole())));
        } else {
            return new ResponseEntity<>("User login not successfully", HttpStatus.ACCEPTED);
        }
    }
}

