package com.finplanner.finplanner.mapper;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.dto.budget.CreateBudgetDto;
import com.finplanner.finplanner.dto.budget.PatchBudgetDto;
import com.finplanner.finplanner.dto.budget.UpdateBudgetDto;
import com.finplanner.finplanner.model.Budget;
import com.finplanner.finplanner.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Budget toBudget(CreateBudgetDto dto, User user);

    @Mapping(target = "categoryName", source = "category.name")
    BudgetDto toBudgetDto(Budget budget);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateBudgetFromUpdateDto(UpdateBudgetDto dto, Budget budget);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBudgetFromPatchDto(PatchBudgetDto dto, Budget budget);

    List<BudgetDto> toBudgetDtoList(List<Budget> budgets);
}
