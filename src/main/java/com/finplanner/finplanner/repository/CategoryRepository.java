package com.finplanner.finplanner.repository;

import com.finplanner.finplanner.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByIdAndUserId(UUID id, UUID userId);

    Optional<Category> findByIdAndUserIsNull(UUID id);

    List<Category> findAllByUserIdOrUserIsNull(UUID userId);
}
