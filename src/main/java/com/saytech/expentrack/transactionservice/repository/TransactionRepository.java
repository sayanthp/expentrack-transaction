package com.saytech.expentrack.transactionservice.repository;

import com.saytech.expentrack.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCategory(Long userId, String category);

}
