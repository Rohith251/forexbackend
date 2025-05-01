package com.forexcard.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forexcard.dto.PendingUserDTO;
import com.forexcard.exception.ResourceNotFoundException;
import com.forexcard.model.ForexCard;
import com.forexcard.model.User;
import com.forexcard.repo.ForexCardRepository;
import com.forexcard.repo.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ForexCardRepository cardRepo;

    @Autowired
    private EmailService emailservice;

    private static final SecureRandom secureRandom = new SecureRandom();

    // Approve user and generate card
    public String approveUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        
        user.setAdminAction("APPROVED");

        if (user.getForexCard() == null) {
            ForexCard card = new ForexCard();
            card.setCardNumber(generateCardNumber());
            card.setCvv(generateCVV());
            card.setIssueDate(LocalDate.now());
            card.setExpiryDate(LocalDate.now().plusYears(5));
            card.setStatus("INACTIVE");
            card.setUser(user);

            cardRepo.save(card);
            user.setForexCard(card);
        }

        userRepo.save(user);
        emailservice.sendCardApprovalConfirmation(user.getEmail());

        return "User approved and card created.";
    }

    public String denyUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        
        user.setAdminAction("REJECTED");
        userRepo.save(user);
        emailservice.sendCardRejectionEmail(user.getEmail());
        return "User application has been denied.";
    }
    
 // Generate 16-digit secure card number
    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(secureRandom.nextInt(10)); // 0–9
        }
        return cardNumber.toString();
    }

    // Generate secure 3-digit CVV
    private String generateCVV() {
        return String.format("%03d", secureRandom.nextInt(1000));
    }

    // Get users whose adminAction is PENDING
    public List<PendingUserDTO> getPendingUsers() {
        return userRepo.findPendingUsers("PENDING");
    }

}

