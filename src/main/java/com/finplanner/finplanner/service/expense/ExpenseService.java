package com.finplanner.finplanner.service.expense;

import com.finplanner.finplanner.dto.expense.CreateExpenseDto;
import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.dto.expense.PatchExpenseDto;
import com.finplanner.finplanner.model.User;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<ExpenseDto> getUserExpenses(User user);
    ExpenseDto createExpense(CreateExpenseDto createExpenseDto, User user);
    ExpenseDto replaceExpense(UUID expenseId, CreateExpenseDto createExpenseDto, User user);
    ExpenseDto patchExpense(UUID expenseId, PatchExpenseDto patchExpenseDto, User user);
    void deleteExpense(UUID expenseId, User user);
}
