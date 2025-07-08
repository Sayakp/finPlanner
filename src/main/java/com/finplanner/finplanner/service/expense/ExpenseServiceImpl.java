package com.finplanner.finplanner.service.expense;

import com.finplanner.finplanner.dto.expense.CreateExpenseDto;
import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.dto.expense.PatchExpenseDto;
import com.finplanner.finplanner.exception.ResourceNotFoundException;
import com.finplanner.finplanner.mapper.ExpenseMapper;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.Expense;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.CategoryRepository;
import com.finplanner.finplanner.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements  ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<ExpenseDto> getUserExpenses(User user) {
        List<Expense> expenses = expenseRepository.findByUserId(user.getId());
        return expenseMapper.toExpenseDtoList(expenses);
    }

    @Override
    public ExpenseDto createExpense(CreateExpenseDto createExpenseDto, User user) {
        Category category = resolveAccessibleCategory(createExpenseDto.getCategoryId(), user);

        Expense expense = new Expense();
        expense.setAmount(createExpenseDto.getAmount());
        expense.setDescription(createExpenseDto.getDescription());
        expense.setDate(createExpenseDto.getDate());
        expense.setCategory(category);
        expense.setUser(user);
        expenseRepository.save(expense);

        return expenseMapper.toExpenseDto(expense);
    }

    @Override
    @Transactional
    public ExpenseDto replaceExpense(UUID expenseId, CreateExpenseDto createExpenseDto, User user) {
        Expense expenseToReplace = findAuthorizedExpense(expenseId, user);
        Category category = resolveAccessibleCategory(createExpenseDto.getCategoryId(), user);

        expenseToReplace.setCategory(category);
        expenseToReplace.setAmount(createExpenseDto.getAmount());
        expenseToReplace.setDescription(createExpenseDto.getDescription());
        expenseToReplace.setDate(createExpenseDto.getDate());
        expenseRepository.save(expenseToReplace);

        return expenseMapper.toExpenseDto(expenseToReplace);
    }

    @Override
    @Transactional
    public ExpenseDto patchExpense(UUID expenseId, PatchExpenseDto patchExpenseDto, User user) {
        Expense expenseToPatch = findAuthorizedExpense(expenseId, user);

        if(patchExpenseDto.getAmount()!=null){
            expenseToPatch.setAmount(patchExpenseDto.getAmount());
        }
        if(patchExpenseDto.getCategoryId()!=null){
            Category category = resolveAccessibleCategory(patchExpenseDto.getCategoryId(), user);
            expenseToPatch.setCategory(category);
        }
        if(patchExpenseDto.getDate()!=null){
            expenseToPatch.setDate(patchExpenseDto.getDate());
        }
        if(patchExpenseDto.getDescription()!=null){
            expenseToPatch.setDescription(patchExpenseDto.getDescription());
        }
        expenseRepository.save(expenseToPatch);
        return expenseMapper.toExpenseDto(expenseToPatch);
    }

    @Override
    public void deleteExpense(UUID expenseId, User user) {
        Expense expenseToDelete = findAuthorizedExpense(expenseId, user);
        expenseRepository.delete(expenseToDelete);
    }

    private Expense findAuthorizedExpense(UUID expenseId, User user) {
        return user.isAdmin()
                ? expenseRepository.findById(expenseId).orElseThrow(
                        ()-> new ResourceNotFoundException("Expense not found"))
                : expenseRepository.findByIdAndUserId(expenseId, user.getId()).orElseThrow(
                        ()-> new ResourceNotFoundException("Expense not found or not authorized")
        );
    }

    private Category resolveAccessibleCategory(UUID categoryId, User user) {
        return categoryRepository.findByIdAndUserId(categoryId, user.getId())
                .or(() -> categoryRepository.findByIdAndUserIsNull(categoryId))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
