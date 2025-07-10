package com.finplanner.finplanner.controller;

import com.finplanner.finplanner.dto.category.CategoryDto;
import com.finplanner.finplanner.dto.category.CreateCategoryDto;
import com.finplanner.finplanner.dto.category.PatchCategoryDto;
import com.finplanner.finplanner.dto.category.UpdateCategoryDto;
import com.finplanner.finplanner.security.UserPrincipal;
import com.finplanner.finplanner.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories(@AuthenticationPrincipal UserPrincipal principal) {
        List<CategoryDto> categories = categoryService.getUserCategories(principal.getUser());
        return ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryDto dto,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        CategoryDto createdCategory = categoryService.createCategory(dto, principal.getUser());
        return ResponseEntity.status(201).body(createdCategory);
    }

    @PostMapping("/global")
    public ResponseEntity<CategoryDto> createGlobalCategory(@RequestBody @Valid CreateCategoryDto dto) {
        CategoryDto createdGlobalCategory = categoryService.createGlobalCategory(dto);
        return ResponseEntity.status(201).body(createdGlobalCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> replaceCategory(@PathVariable UUID id,
                                                       @RequestBody @Valid UpdateCategoryDto dto,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        CategoryDto updatedCategory = categoryService.replaceCategory(id, dto, principal.getUser());
        return ResponseEntity.ok(updatedCategory);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> patchCategory(@PathVariable UUID id,
                                                     @RequestBody @Valid PatchCategoryDto dto,
                                                     @AuthenticationPrincipal UserPrincipal principal) {
        CategoryDto patchedCategory = categoryService.patchCategory(id, dto, principal.getUser());
        return ResponseEntity.ok(patchedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id,
                                               @AuthenticationPrincipal UserPrincipal principal) {
        categoryService.deleteCategory(id, principal.getUser());
        return ResponseEntity.noContent().build();
    }
}
