package com.finplanner.finplanner.service.budget;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.mapper.BudgetMapper;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.BudgetRepository;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

public class BudgetServiceImpl implements  BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Override
    public List<BudgetDto> getUserBudgets(User user) {
        return List.of();
    }

    @Override
    public BudgetDto getBudgetById(UUID budgetId, User user) {
        return null;
    }

    @Override
    public BudgetDto createBudget(BudgetDto budgetDto, User user) {
        return null;
    }

    @Override
    public BudgetDto replaceBudget(UUID budgetId, BudgetDto budgetDto, User user) {
        return null;
    }

    @Override
    public BudgetDto patchBudget(UUID budgetId, BudgetDto budgetDto, User user) {
        return null;
    }

    @Override
    public void deleteBudget(UUID budgetId, User user) {

    }
}
