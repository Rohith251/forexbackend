package com.forexcard.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Currency {
	
	@Id
    private String code;

    private String name;
    private Double exchangeRate;

    @OneToMany(mappedBy = "currency")
    private List<Transaction> transactions;

	public Currency() {
		super();
		// TODO Auto-generated constructor stub
	}

	



	public Currency(String code, Double exchangeRate, String name, List<Transaction> transactions) {
		super();
		this.code = code;
		this.name = name;
		this.exchangeRate = exchangeRate;
		this.transactions = transactions;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Currency [code=" + code + ", name=" + name + ", exchangeRate=" + exchangeRate + ", transactions="
				+ transactions + "]";
	}
    
    
    

}