package com.forexcard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;
import com.forexcard.util.JwtUtil;
import com.forexcard.exception.AuthenticationFailedException;  // Custom exception for failed login

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder should be configured

    // Method for logging in the user
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            throw new AuthenticationFailedException("Invalid email or password.");
        }

        return userOpt.get();
    }


    // Method to get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null); // Return null if user is not found
    }

    // Method to register new user (storing hashed password)
    
    public String getTokenForUser(User user) {
        return jwtUtil.generateToken(user.getEmail());
    }

    
}
