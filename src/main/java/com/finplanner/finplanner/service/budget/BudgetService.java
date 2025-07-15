package com.finplanner.finplanner.service.budget;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.dto.budget.CreateBudgetDto;
import com.finplanner.finplanner.dto.budget.PatchBudgetDto;
import com.finplanner.finplanner.dto.budget.UpdateBudgetDto;
import com.finplanner.finplanner.model.User;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    List<BudgetDto> getUserBudgets(User user);

    BudgetDto getBudgetById(UUID budgetId, User user);

    BudgetDto createBudget(CreateBudgetDto budgetDto, User user);

    BudgetDto replaceBudget(UUID budgetId, UpdateBudgetDto budgetDto, User user);

    BudgetDto patchBudget(UUID budgetId, PatchBudgetDto budgetDto, User user);

    void deleteBudget(UUID budgetId, User user);
}
