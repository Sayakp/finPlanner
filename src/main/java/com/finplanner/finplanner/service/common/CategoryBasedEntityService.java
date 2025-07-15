package com.finplanner.finplanner.service.common;

import com.finplanner.finplanner.exception.ResourceNotFoundException;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

public abstract class CategoryBasedEntityService<T> {
    protected final CategoryRepository categoryRepository;

    protected CategoryBasedEntityService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    protected T findAuthorizedEntity(UUID entityId, User user) {
        String entityName = getEntityName();
        return user.isAdmin()
                ? findByIdForAdmin(entityId).orElseThrow(
                () -> new ResourceNotFoundException(entityName + " not found"))
                : findByIdAndUserId(entityId, user.getId()).orElseThrow(
                () -> new ResourceNotFoundException(entityName + " not found or not authorized"));
    }

    protected Category resolveAccessibleCategory(UUID categoryId, User user) {
        return categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .or(() -> categoryRepository.findByIdAndUserIsNull(categoryId))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    protected abstract Optional<T> findByIdForAdmin(UUID entityId);

    protected abstract Optional<T> findByIdAndUserId(UUID entityId, UUID userId);

    protected abstract String getEntityName();
}
