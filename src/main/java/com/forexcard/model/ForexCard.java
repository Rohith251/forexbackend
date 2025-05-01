package com.forexcard.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class ForexCard {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status ;
    private double maxLimit;
    private double balance ;
    private String pin;
    private String cvv;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference(value = "card-transactions")
    @OneToMany(mappedBy = "forexCard")
    private List<Transaction> transactions;
    
    

	public ForexCard() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ForexCard(Long id, String cardNumber, LocalDate issueDate, LocalDate expiryDate, String status,
			double maxLimit, double balance, String pin, String cvv, User user, List<Transaction> transactions) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		this.status = status;
		this.maxLimit = maxLimit;
		this.balance = balance;
		this.pin = pin;
		this.cvv = cvv;
		this.user = user;
		this.transactions = transactions;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCardNumber() {
		return cardNumber;
	}



	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	public LocalDate getIssueDate() {
		return issueDate;
	}



	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}



	public LocalDate getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public double getMaxLimit() {
		return maxLimit;
	}



	public void setMaxLimit(double maxLimit) {
		this.maxLimit = maxLimit;
	}



	public double getBalance() {
		return balance;
	}



	public void setBalance(double d) {
		this.balance = d;
	}



	public String getPin() {
		return pin;
	}



	public void setPin(String pin) {
		this.pin = pin;
	}



	public String getCvv() {
		return cvv;
	}



	public void setCvv(String cvv) {
		this.cvv = cvv;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public List<Transaction> getTransactions() {
		return transactions;
	}



	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}



	@Override
	public String toString() {
		return "ForexCard [id=" + id + ", cardNumber=" + cardNumber + ", issueDate=" + issueDate + ", expiryDate="
				+ expiryDate + ", status=" + status + ", maxLimit=" + maxLimit + ", balance=" + balance + ", pin=" + pin
				+ ", cvv=" + cvv + ", user=" + user + ", transactions=" + transactions + "]";
	}
	
	
}



	