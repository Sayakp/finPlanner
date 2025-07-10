package com.finplanner.finplanner.service.category;

import com.finplanner.finplanner.dto.category.CategoryDto;
import com.finplanner.finplanner.dto.category.CreateCategoryDto;
import com.finplanner.finplanner.dto.category.PatchCategoryDto;
import com.finplanner.finplanner.dto.category.UpdateCategoryDto;
import com.finplanner.finplanner.exception.ResourceNotFoundException;
import com.finplanner.finplanner.mapper.CategoryMapper;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getUserCategories(User user) {
        List<Category> categories = categoryRepository.findAllByUserIdOrUserIsNull(user.getId());
        return categoryMapper.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto createCategory(CreateCategoryDto categoryDto, User user) {
        Category category = categoryMapper.toCategory(categoryDto, user);
        categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createGlobalCategory(CreateCategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto replaceCategory(UUID categoryId, UpdateCategoryDto categoryDto, User user) {
        Category categoryToReplace = categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or not accessible"));
        categoryMapper.updateCategoryFromUpdateDto(categoryDto, categoryToReplace);
        return categoryMapper.toCategoryDto(categoryToReplace);
    }

    @Override
    @Transactional
    public CategoryDto patchCategory(UUID categoryId, PatchCategoryDto categoryDto, User user) {
        Category categoryToPatch = categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or not accessible"));
        categoryMapper.updateCategoryFromPatchDto(categoryDto, categoryToPatch);
        return categoryMapper.toCategoryDto(categoryToPatch);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId, User user) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or not accessible"));
        categoryRepository.delete(category);
    }
}
