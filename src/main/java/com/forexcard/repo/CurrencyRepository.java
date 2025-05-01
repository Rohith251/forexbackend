package com.forexcard.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forexcard.model.Currency;


@Repository
public interface CurrencyRepository extends JpaRepository<Currency,String> {

}
