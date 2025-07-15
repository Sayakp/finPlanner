package com.finplanner.finplanner.repository;

import com.finplanner.finplanner.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findAllByUserId(UUID userId);

    Optional<Budget> findByIdAndUserId(UUID id, UUID userId);
}
