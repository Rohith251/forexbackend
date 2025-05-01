package com.forexcard.service;

import com.forexcard.dto.PendingUserDTO;
import com.forexcard.dto.UserDTO;
import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Add/Register a new user
    public String addUser(User user) {
        // Check if a user already exists with the same email
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "User already exists with this email";
        }

        // If email is unique, encode the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        emailService.sendRegistrationSuccessEmail(user.getEmail());
       
        return "Register successful";
    }

    public UserDTO getUserDTO(Integer id) {
        return userRepository.findUserDTOById(id);
    }
   

    public List<PendingUserDTO> getUsersByAdminAction(String action) {
        return userRepository.findPendingUserDTOsByAdminAction(action);
    }

	public PendingUserDTO getUserProfileById(Integer id) {
		return userRepository.findUserProfileByid(id);
	}
	
	public ResponseEntity<String> updateProfile(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        
        User user1 = existingUser.get();
        
        user1.setAddress(user.getAddress());
        user1.setPhonenumber(user.getPhonenumber());
        user1.setState(user.getState());
		userRepository.save(user1); 
		
		return ResponseEntity.ok("Update Successful");
	}
	

}
