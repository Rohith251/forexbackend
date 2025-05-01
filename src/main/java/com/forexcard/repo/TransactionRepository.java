package com.forexcard.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.forexcard.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

	@Query("SELECT t FROM Transaction t WHERE t.forexCard.id = :cardId AND t.date BETWEEN :startDate AND :endDate")
	List<Transaction> findTransactionsByCardIdAndDateBetween(
	    @Param("cardId") Long cardId,
	    @Param("startDate") LocalDateTime startDateTime,
	    @Param("endDate") LocalDateTime endDateTime
	);

	 List<Transaction> findByForexCardId(Long forexCardId);



}
