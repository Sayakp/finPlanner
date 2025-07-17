package com.finplanner.finplanner.controller;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.dto.budget.CreateBudgetDto;
import com.finplanner.finplanner.dto.budget.PatchBudgetDto;
import com.finplanner.finplanner.dto.budget.UpdateBudgetDto;
import com.finplanner.finplanner.security.UserPrincipal;
import com.finplanner.finplanner.service.budget.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping()
    public ResponseEntity<List<BudgetDto>> getBudgets(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<BudgetDto> budgets = budgetService.getUserBudgets(userPrincipal.getUser());
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> getBudgetById(@PathVariable UUID budgetId,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        BudgetDto budget = budgetService.getBudgetById(budgetId, userPrincipal.getUser());
        return ResponseEntity.ok(budget);
    }

    @PostMapping()
    public ResponseEntity<BudgetDto> createBudget(@RequestBody @Valid CreateBudgetDto budgetDto,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        BudgetDto createdBudget = budgetService.createBudget(budgetDto, userPrincipal.getUser());
        return ResponseEntity.status(201).body(createdBudget);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> replaceBudget(@PathVariable UUID budgetId,
                                                   @RequestBody @Valid UpdateBudgetDto budgetDto,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        BudgetDto updatedBudget = budgetService.replaceBudget(budgetId, budgetDto, userPrincipal.getUser());
        return ResponseEntity.ok(updatedBudget);
    }

    @PatchMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> patchBudget(@PathVariable UUID budgetId,
                                                 @RequestBody @Valid PatchBudgetDto budgetDto,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        BudgetDto patchedBudget = budgetService.patchBudget(budgetId, budgetDto, userPrincipal.getUser());
        return ResponseEntity.ok(patchedBudget);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID budgetId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        budgetService.deleteBudget(budgetId, userPrincipal.getUser());
        return ResponseEntity.noContent().build();
    }
}
