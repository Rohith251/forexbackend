package com.forexcard.controller;

import com.forexcard.model.Transaction;

import com.forexcard.model.User;
import com.forexcard.pdf.PdfGeneratorService;
import com.forexcard.repo.TransactionRepository;
import com.forexcard.repo.UserRepository;
import com.forexcard.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmailController(EmailService emailService,
                            TransactionRepository transactionRepository,
                            UserRepository userRepository) {
        this.emailService = emailService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint to send OTP to user's email for password reset
     */
    @PostMapping("/send-forgot-password-otp")
    public ResponseEntity<String> sendForgotPasswordOtp(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        emailService.sendForgotPasswordOtp(userOpt.get().getId()); // Trigger OTP sending for password reset
        return ResponseEntity.ok("OTP for password reset has been sent successfully.");
    }
    /**
     * Endpoint to send OTP to user's email
     */
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam("userId") Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        emailService.sendOtp(userId); // Trigger OTP sending
        return ResponseEntity.ok("OTP sending triggered successfully.");
    }

    /**
     * Endpoint to send transaction report as PDF via email
     */
    @GetMapping("/report")
    public ResponseEntity<String> sendTransactionReportToUser(
            @RequestParam("userId") Integer userId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty() || userOpt.get().getForexCard() == null) {
            return ResponseEntity.badRequest().body("User or Forex Card not found.");
        }

        User user = userOpt.get();
        Long cardId = user.getForexCard().getId();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionRepository
                .findTransactionsByCardIdAndDateBetween(cardId, startDateTime, endDateTime);

        if (transactions.isEmpty()) {
            return ResponseEntity.ok("No transactions found for the selected period.");
        }

        byte[] pdfBytes = PdfGeneratorService.generateTransactionReport(transactions, user);

        emailService.sendTransactionReport(user.getEmail(), pdfBytes, "Transaction_Report.pdf");
		return ResponseEntity.ok("Transaction report sent to your registered email.");
    }
}
