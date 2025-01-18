package com.saytech.expentrack.transactionservice.repository;

import com.saytech.expentrack.transactionservice.entity.Budget;
import com.saytech.expentrack.transactionservice.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserIdAndCategory(Long userId, CategoryType category);

    List<Budget> findByUserId(Long userId);
}
