package com.forexcard.dataload;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.forexcard.model.Currency;
import com.forexcard.repo.CurrencyRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
    private CurrencyRepository repo;

    

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            List<com.forexcard.model.Currency> currencies = List.of(
                new Currency("INR", 1.00, "Indian Rupee",null),
                new Currency("USD", 83.33, "US Dollar",null),
                new Currency("EUR", 90.91, "Euro",null),
                new Currency("JPY", 0.67, "Japanese Yen",null),
                new Currency("GBP", 104.17, "British Pound",null),
                new Currency("AUD", 55.25, "Australian Dollar",null),
                new Currency("CAD", 61.75, "Canadian Dollar",null),
                new Currency("CHF", 93.50, "Swiss Franc",null),
                new Currency("SGD", 61.00, "Singapore Dollar",null),
                new Currency("CNY", 11.50, "Chinese Yuan",null),
                new Currency("ZAR", 4.55, "South African Rand",null),
                new Currency("NZD", 52.30, "New Zealand Dollar",null),
                new Currency("KRW", 0.063, "South Korean Won",null),
                new Currency("THB", 2.41, "Thai Baht",null),
                new Currency("AED", 22.69, "UAE Dirham",null),
                new Currency("HKD", 10.65, "Hong Kong Dollar",null),
                new Currency("SEK", 8.20, "Swedish Krona",null),
                new Currency("NOK", 7.85, "Norwegian Krone",null),
                new Currency("DKK", 12.19, "Danish Krone",null),
                new Currency("MYR", 17.92, "Malaysian Ringgit",null),
                new Currency("IDR", 0.0054, "Indonesian Rupiah",null),
                new Currency("PHP", 1.50, "Philippine Peso",null)
            );
            repo.saveAll(currencies);
        }
    }
}