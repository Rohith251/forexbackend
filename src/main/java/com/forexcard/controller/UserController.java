package com.forexcard.controller;

import com.forexcard.dto.PendingUserDTO;
import com.forexcard.dto.UserDTO;
import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;
import com.forexcard.service.EmailService;
import com.forexcard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;


    @Autowired
    private UserService userService;



    // Register/Add User
    @PostMapping("/addUser")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) {
        String result = userService.addUser(user);
        boolean success = "Register successful".equalsIgnoreCase(result);

        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        response.put("success", success);
        

        return new ResponseEntity<>(response, success ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer id) {
        // Fetch the UserDTO from the service layer
        UserDTO userDTO = userService.getUserDTO(id);

        // Return the response
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<PendingUserDTO> getUserProfileById(@PathVariable("userId") Integer id) {
        // Fetch the UserDTO from the service layer
        UserDTO userDTO = userService.getUserDTO(id);
        PendingUserDTO pendingdto =userService.getUserProfileById(id);
        // Return the response
        return ResponseEntity.ok(pendingdto);
    }
    @GetMapping("/approved")
    public List<PendingUserDTO> getApprovedUsers() {
        return userService.getUsersByAdminAction("APPROVED");
    }

    @GetMapping("/rejected")
    public List<PendingUserDTO> getRejectedUsers() {
        return userService.getUsersByAdminAction("REJECTED");
    }
    
    @PutMapping("/update-profiledetalis")
    public ResponseEntity<String> updateProfile(@RequestBody User user)
    {
    	return userService.updateProfile(user);
    }
   
    
    @PatchMapping("/verify-otp-and-update-email")
    public ResponseEntity<String> verifyOtpAndUpdateEmail(@RequestParam("userId") Integer userId,
                                                          @RequestParam("otp") String otp,
                                                          @RequestParam("newEmail") String newEmail) {
        String storedOtp = emailService.getStoredOtp(userId);

        if (storedOtp == null) {
            return ResponseEntity.badRequest().body("OTP has expired or not generated yet.");
        }

        if (!otp.equals(storedOtp)) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        User user = userOpt.get();
        user.setEmail(newEmail);
        userRepository.save(user);
        emailService.clearOtp(userId);

        return ResponseEntity.ok("Email updated successfully.");
    }

    

}
