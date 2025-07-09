package com.finplanner.finplanner.service.category;

import com.finplanner.finplanner.dto.category.CategoryDto;
import com.finplanner.finplanner.dto.category.CreateCategoryDto;
import com.finplanner.finplanner.dto.category.PatchCategoryDto;
import com.finplanner.finplanner.dto.category.UpdateCategoryDto;
import com.finplanner.finplanner.model.User;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> getUserCategories(User user);

    CategoryDto createCategory(CreateCategoryDto categoryDto, User user);

    CategoryDto createGlobalCategory(CreateCategoryDto categoryDto);

    CategoryDto replaceCategory(UUID categoryId, UpdateCategoryDto categoryDto, User user);

    CategoryDto patchCategory(UUID categoryId, PatchCategoryDto categoryDto, User user);

    void deleteCategory(UUID categoryId, User user);

}
