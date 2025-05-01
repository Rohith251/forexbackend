package com.forexcard.repo;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.forexcard.model.ForexCard;

@Repository
public interface ForexCardRepository extends JpaRepository<ForexCard, Long> {

	

	 Optional<ForexCard> findByUserId(Integer userId);

	Optional<ForexCard> findByCardNumber(String cardNumber);  
    
}

