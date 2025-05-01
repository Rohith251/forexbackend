package com.forexcard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forexcard.model.Transaction;
import com.forexcard.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    
    @Autowired
    private TransactionService service;
    
    // Endpoint to process a transaction (deduct amount)
    @PostMapping("/process")
    public ResponseEntity<?> makeTransaction(@RequestBody Transaction request) {
        return service.processTransaction(request); // Delegate to the service layer
    }
    
    // Endpoint to get transactions for a specific user by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable("userId") Integer userId) {
        List<Transaction> transactions = service.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions); // Return the list of transactions as response
    }
    
    @GetMapping("/transactionsByDate")
	  public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
	          @RequestParam("userId") Integer userId,
	          @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

	      List<Transaction> transactions = service.getTransactionsByDateRange(userId, startDate, endDate);
	      return ResponseEntity.ok(transactions);
	  }
}
