package com.finplanner.finplanner.repository;

import com.finplanner.finplanner.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
    Income findByIdAndUserId(UUID id, UUID userId);

    List<Income> findByUserId(UUID userId);
}
