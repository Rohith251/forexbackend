package com.forexcard.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.forexcard.model.Currency;
import com.forexcard.model.ForexCard;
import com.forexcard.model.Transaction;
import com.forexcard.repo.CurrencyRepository;
import com.forexcard.repo.ForexCardRepository;
import com.forexcard.repo.TransactionRepository;

@Service
public class TransactionService {
	
	    @Autowired
	    private ForexCardRepository forexCardRepository;

	    @Autowired
	    private CurrencyRepository currencyRepository;

	    @Autowired
	    private TransactionRepository transactionRepository;

	    public ResponseEntity<?> processTransaction(Transaction transaction) {
	        Long cardId = transaction.getForexCard().getId();
	        String currencyCode = transaction.getCurrency().getCode();
	        String pin = transaction.getForexCard().getPin();

	        Optional<ForexCard> cardOpt = forexCardRepository.findById(cardId);
	        Optional<Currency> currencyOpt = currencyRepository.findById(currencyCode);

	        Currency currency = currencyOpt.orElse(null);
	        ForexCard card = cardOpt.orElse(null);

	        Transaction tx = new Transaction();
	        tx.setForexCard(transaction.getForexCard());
	        tx.setCurrency(currency);
	        tx.setAmount(transaction.getAmount());
	        tx.setMerchant(transaction.getMerchant());
	        tx.setDate(LocalDateTime.now());

	        // Invalid card or currency
	        if (card == null || currency == null) {
	            tx.setStatus("FAILED");
	            tx.setDeductAmount(0);
	            tx.setCurrentBalance(0);
	            transactionRepository.save(tx);
	            return ResponseEntity.badRequest().body("Invalid card or currency.");
	        }

	        // Wrong PIN
	        if (!card.getPin().equals(pin)) {
	            tx.setStatus("FAILED");
	            tx.setDeductAmount(0);
	            tx.setCurrentBalance(card.getBalance());
	            transactionRepository.save(tx);
	            return ResponseEntity.badRequest().body("Invalid pin.");
	        }

	        // Calculate total deduction
	        double amountInBaseCurrency = transaction.getAmount() * currency.getExchangeRate() + 100;

	        // Card blocked
	        if (card.getStatus().equalsIgnoreCase("BLOCKED")) {
	            tx.setStatus("FAILED");
	            tx.setDeductAmount(amountInBaseCurrency);
	            tx.setCurrentBalance(card.getBalance());
	            transactionRepository.save(tx);
	            return ResponseEntity.badRequest().body("Card Blocked.");
	        }

	        // Insufficient funds
	        if (card.getBalance() < amountInBaseCurrency) {
	            tx.setStatus("FAILED");
	            tx.setDeductAmount(amountInBaseCurrency);
	            tx.setCurrentBalance(card.getBalance());
	            transactionRepository.save(tx);
	            return ResponseEntity.badRequest().body("Insufficient balance.");
	        }

	        // Successful transaction: deduct and save
	        card.setBalance(card.getBalance() - amountInBaseCurrency);
	        forexCardRepository.save(card);
	        String randomOrderId = "ORDER_" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
	        tx.setOrderId(randomOrderId);
	        tx.setStatus("SUCCESS");
	        tx.setDeductAmount(amountInBaseCurrency);
	        tx.setCurrentBalance(card.getBalance()); // Now reflects new balance after deduction
	        transactionRepository.save(tx);

	        return ResponseEntity.ok("Transaction successful!");
	    }


	    
	    public List<Transaction> getTransactionsByUserId(Integer userId) {
	        ForexCard forexCard = forexCardRepository.findByUserId(userId)
	                .orElseThrow(() -> new RuntimeException("Forex Card not found for userId: " + userId));

	        Long forexCardId = forexCard.getId();
	        return transactionRepository.findByForexCardId(forexCardId);
	    }

	    public List<Transaction> getTransactionsByDateRange(Integer userId, LocalDate startDate, LocalDate endDate) {
	        ForexCard forexCard = forexCardRepository.findByUserId(userId)
	                .orElseThrow(() -> new RuntimeException("Forex Card not found for userId: " + userId));

	        Long cardId = forexCard.getId();
	        LocalDateTime startDateTime = startDate.atStartOfDay();
	        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

	        return transactionRepository.findTransactionsByCardIdAndDateBetween(cardId, startDateTime, endDateTime);
	    }

}


