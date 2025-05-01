package com.forexcard.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime date;
	private double amount;
	private String merchant;
	private String status;
	private double currentBalance;
	private double deductAmount;
	private String orderId;
	
	


	@JsonBackReference(value = "card-transactions")
	@ManyToOne
	@JoinColumn(name = "cardId")
	private ForexCard forexCard;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "currency")
	private Currency currency;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Long id, LocalDateTime date, double amount, String merchant, String status,
			double currentBalance, double deductAmount, String orderId, ForexCard forexCard, Currency currency) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.merchant = merchant;
		this.status = status;
		this.currentBalance = currentBalance;
		this.deductAmount = deductAmount;
		this.orderId = orderId;
		this.forexCard = forexCard;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public double getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(double deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ForexCard getForexCard() {
		return forexCard;
	}

	public void setForexCard(ForexCard forexCard) {
		this.forexCard = forexCard;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + ", merchant=" + merchant
				+ ", status=" + status + ", currentBalance=" + currentBalance + ", deductAmount=" + deductAmount
				+ ", orderId=" + orderId + ", forexCard=" + forexCard + ", currency=" + currency + "]";
	}
	
	
	

}
