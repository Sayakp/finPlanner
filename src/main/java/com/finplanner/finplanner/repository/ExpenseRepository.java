package com.finplanner.finplanner.repository;

import com.finplanner.finplanner.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);
    List<Expense> findByUserId(UUID userId);
}
