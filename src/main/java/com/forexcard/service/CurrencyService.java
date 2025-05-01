package com.forexcard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forexcard.exception.ResourceNotFoundException;
import com.forexcard.model.Currency;
import com.forexcard.repo.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repo;

    // Get all available currencies
    public List<Currency> getAllCurrencies() {
        return repo.findAll();
    }

    // Get exchange rate for a specific currency code
    public Double getExchangeRateByCode(String code) {
        return repo.findById(code)
            .map(Currency::getExchangeRate)
            .orElseThrow(() -> new ResourceNotFoundException("Currency code not found: " + code));
    }
}
