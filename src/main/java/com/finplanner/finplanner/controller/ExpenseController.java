package com.finplanner.finplanner.controller;

import com.finplanner.finplanner.dto.expense.CreateExpenseDto;
import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.dto.expense.PatchExpenseDto;
import com.finplanner.finplanner.security.UserPrincipal;
import com.finplanner.finplanner.service.expense.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public List<ExpenseDto> getExpenses(@AuthenticationPrincipal UserPrincipal principal) {
        return expenseService.getUserExpenses(principal.getUser());
    }

    @PostMapping()
    public ExpenseDto createExpense(@RequestBody @Valid CreateExpenseDto createExpenseDto,
                                    @AuthenticationPrincipal UserPrincipal principal) {
        return expenseService.createExpense(createExpenseDto, principal.getUser());
    }

    @PutMapping("/{id}")
    public ExpenseDto replaceExpense(@PathVariable UUID id,
                                     @RequestBody @Valid CreateExpenseDto createExpenseDto,
                                     @AuthenticationPrincipal UserPrincipal principal) {
        return expenseService.replaceExpense(id, createExpenseDto, principal.getUser());
    }

    @PatchMapping("/{id}")
    public ExpenseDto patchExpense(@PathVariable UUID id,
                                   @RequestBody @Valid PatchExpenseDto patchExpenseDto,
                                   @AuthenticationPrincipal UserPrincipal principal) {
        return expenseService.patchExpense(id, patchExpenseDto, principal.getUser());
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id,
                              @AuthenticationPrincipal UserPrincipal principal) {
        expenseService.deleteExpense(id, principal.getUser());
    }

}
