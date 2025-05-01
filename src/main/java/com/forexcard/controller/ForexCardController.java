package com.forexcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forexcard.model.ForexCard;
import com.forexcard.service.EmailService;
import com.forexcard.service.ForexCardService;
import com.forexcard.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/card")
public class ForexCardController {

    @Autowired
    private ForexCardService forexCardService;

    @Autowired
    private EmailService emailService;

    // Send OTP before card activation
    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam("userId") Integer userId) {
        emailService.sendOtp(userId);
        return ResponseEntity.ok("OTP sent successfully to your registered email.");
    }

    // Verify OTP and activate card
    @PostMapping("/verifyOtpAndActivate")
    public ResponseEntity<String> verifyOtpAndActivate(@RequestParam("userId") Integer userId,
                                                       @RequestParam("otp") String otp,
                                                       @RequestParam("pin") String pin) {
        String storedOtp = emailService.getStoredOtp(userId);

        if (storedOtp == null) {
            return ResponseEntity.badRequest().body("OTP has expired or not generated yet.");
        }

        if (!otp.equals(storedOtp)) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        if (pin == null || pin.length() != 4) {
            return ResponseEntity.badRequest().body("Invalid PIN. It must be exactly 4 digits.");
        }

        forexCardService.activateCard(userId, pin);
        emailService.clearOtp(userId);
        return ResponseEntity.ok("Card activated successfully.");
    }

    // Block card
    @PostMapping("/block")
    public ResponseEntity<String> blockCard(@RequestParam("userId") Integer userId) {
        String message = forexCardService.blockCardByCardId(userId);
        return ResponseEntity.ok(message);
    }

    // Activate card after blocking
    @PostMapping("/activate")
    public ResponseEntity<String> activateCardByCardNumber(@RequestParam("cardNumber") String cardNumber,
                                                           @RequestParam("pin") String pin) {
        return forexCardService.activateCard(cardNumber, pin);
    }

    // Get card by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<ForexCard> getCardByUserId(@PathVariable("userId") Integer userId) {
        ForexCard card = forexCardService.getCardByUserId(userId);
        return ResponseEntity.ok(card);
    }
}
