package com.forexcard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forexcard.model.ForexCard;
import com.forexcard.model.User;
import com.forexcard.repo.ForexCardRepository;
import com.forexcard.repo.UserRepository;
import com.forexcard.exception.ResourceNotFoundException;

@Service
public class ForexCardService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ForexCardRepository cardRepo;

    @Autowired
    private EmailService emailService;

    // Activate card by setting a PIN (only if admin approved and card exists)
    public String activateCard(Integer userId, String pin) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        
        if (!"APPROVED".equalsIgnoreCase(user.getAdminAction())) {
            throw new ResourceNotFoundException("User not approved by admin.");
        }

        ForexCard card = user.getForexCard();
        if (card == null) {
            throw new ResourceNotFoundException("No Forex Card associated with user.");
        }

        if("BLOCKED".equalsIgnoreCase(card.getStatus()))
        {
        	card.setPin(pin);
            card.setStatus("ACTIVATED");
            cardRepo.save(card);
            emailService.sendCardActivationConfirmation(user.getEmail());
            return "Card Activated Successfully";
        }

        card.setPin(pin);
        card.setStatus("ACTIVATED");

        // Set initial balance and max limit based on salary
        setCardBalanceAndLimits(user, card);

        cardRepo.save(card);
        emailService.sendCardActivationConfirmation(user.getEmail());

        return "Card activated successfully.";
    }

    private void setCardBalanceAndLimits(User user, ForexCard card) {
        double salary = user.getSalary();
        if (salary >= 50000 && salary < 100000) {
            card.setBalance(100000.0);
            card.setMaxLimit(100000.0);
        } else if (salary >= 100000 && salary < 150000) {
            card.setBalance(200000.0);
            card.setMaxLimit(200000.0);
        } else if (salary >= 150000) {
            card.setBalance(300000.0);
            card.setMaxLimit(300000.0);
        }
    }

    // Block the user's card (sets status to BLOCKED)
    public String blockCardByCardId(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        ForexCard forexCard = user.getForexCard();

        if (forexCard == null) {
            throw new ResourceNotFoundException("No Forex Card associated with user.");
        }

        if ("BLOCKED".equalsIgnoreCase(forexCard.getStatus())) {
            throw new ResourceNotFoundException("Card is already blocked!");
        }

        forexCard.setStatus("BLOCKED");
        cardRepo.save(forexCard);

        return "Forex Card has been successfully blocked.";
    }

    // Activate card by card number (sets the status to ACTIVATED after PIN match)
    public ResponseEntity<String> activateCard(String cardNumber, String pin) {
        ForexCard card = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new ResourceNotFoundException("Card not found with number: " + cardNumber));
        
        Integer userId = card.getUser().getId();
        
        Optional<User> useropt = userRepo.findById(userId);
        
        User user = useropt.get();

        if (card.getPin() == null || card.getPin().isEmpty()) {
            return ResponseEntity.badRequest().body("PIN is not set. Please set your PIN before activation.");
        }

        if (!card.getPin().equals(pin)) {
            return ResponseEntity.badRequest().body("Invalid PIN. Please try again.");
        }

        card.setStatus("ACTIVATED");
        cardRepo.save(card);
        emailService.sendCardActivationConfirmation(user.getEmail());

        return ResponseEntity.ok("Card activated successfully.");
    }

    // Get card details by User ID
    public ForexCard getCardByUserId(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        ForexCard card = user.getForexCard();

        if (card == null) {
            throw new ResourceNotFoundException("Forex Card not found for this user.");
        }

        return card;
    }
}
