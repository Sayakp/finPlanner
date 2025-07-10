package com.finplanner.finplanner.mapper;

import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ExpenseDto toExpenseDto(Expense expense);

    List<ExpenseDto> toExpenseDtoList(List<Expense> expenses);
}
