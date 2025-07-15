package com.finplanner.finplanner.service.budget;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.model.User;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

public interface BudgetService {
    List<BudgetDto> getUserBudgets(User user);

    BudgetDto getBudgetById(UUID budgetId, User user);

    BudgetDto createBudget(BudgetDto budgetDto, User user);

    BudgetDto replaceBudget(UUID budgetId, BudgetDto budgetDto, User user);

    BudgetDto patchBudget(UUID budgetId, BudgetDto budgetDto, User user);

    void deleteBudget(UUID budgetId, User user);
}
