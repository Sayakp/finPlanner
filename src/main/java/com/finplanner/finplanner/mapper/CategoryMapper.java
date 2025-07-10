package com.finplanner.finplanner.mapper;

import com.finplanner.finplanner.dto.category.CategoryDto;
import com.finplanner.finplanner.dto.category.CreateCategoryDto;
import com.finplanner.finplanner.dto.category.PatchCategoryDto;
import com.finplanner.finplanner.dto.category.UpdateCategoryDto;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Category toCategory(CreateCategoryDto dto, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Category toCategory(CreateCategoryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateCategoryFromUpdateDto(UpdateCategoryDto dto, @MappingTarget Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromPatchDto(PatchCategoryDto dto, @MappingTarget Category category);

    List<CategoryDto> toCategoryDtoList(List<Category> categories);
}
