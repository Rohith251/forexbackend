package com.forexcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forexcard.model.Currency;
import com.forexcard.service.CurrencyService;
import com.forexcard.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    @GetMapping("/all")
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = service.getAllCurrencies();
        if (currencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Double> getExchangeRateByCode(@PathVariable("code") String code) {
        Double rate = service.getExchangeRateByCode(code);
        return ResponseEntity.ok(rate);
    }
}
