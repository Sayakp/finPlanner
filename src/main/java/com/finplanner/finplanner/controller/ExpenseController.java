package com.finplanner.finplanner.controller;

import com.finplanner.finplanner.dto.expense.CreateExpenseDto;
import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.dto.expense.PatchExpenseDto;
import com.finplanner.finplanner.security.UserPrincipal;
import com.finplanner.finplanner.service.expense.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ExpenseDto>> getExpenses(@AuthenticationPrincipal UserPrincipal principal) {
        List<ExpenseDto> expenses = expenseService.getUserExpenses(principal.getUser());
        return ResponseEntity.ok(expenses);
    }

    @PostMapping()
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody @Valid CreateExpenseDto dto,
                                                    @AuthenticationPrincipal UserPrincipal principal) {
        ExpenseDto created = expenseService.createExpense(dto, principal.getUser());
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> replaceExpense(@PathVariable UUID id,
                                                     @RequestBody @Valid CreateExpenseDto dto,
                                                     @AuthenticationPrincipal UserPrincipal principal) {
        ExpenseDto updated = expenseService.replaceExpense(id, dto, principal.getUser());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDto> patchExpense(@PathVariable UUID id,
                                                   @RequestBody @Valid PatchExpenseDto dto,
                                                   @AuthenticationPrincipal UserPrincipal principal) {
        ExpenseDto updated = expenseService.patchExpense(id, dto, principal.getUser());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id,
                                              @AuthenticationPrincipal UserPrincipal principal) {
        expenseService.deleteExpense(id, principal.getUser());
        return ResponseEntity.noContent().build();
    }


}
