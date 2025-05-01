package com.forexcard.controller;

import com.forexcard.service.ForgotPasswordService;
import com.forexcard.service.EmailService;
import com.forexcard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reset")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping("/password")
    public ResponseEntity<String> verifyOtpAndResetPassword(@RequestParam("email") String email,
                                                             @RequestParam("otp") String otp,
                                                             @RequestParam("newPassword") String newPassword) {
        // Get stored OTP
        String storedOtp = emailService.getStoredOtpByEmail(email);

        if (storedOtp == null) {
            return ResponseEntity.badRequest().body("OTP has expired or not generated yet.");
        }

        if (!otp.equals(storedOtp)) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        // Validate password rules if needed (optional)
       

        // Reset the password
        forgotPasswordService.resetPassword(email, newPassword);

        // Clear OTP
        emailService.clearOtpByEmail(email);

        return ResponseEntity.ok("Password reset successfully.");
    }
}
