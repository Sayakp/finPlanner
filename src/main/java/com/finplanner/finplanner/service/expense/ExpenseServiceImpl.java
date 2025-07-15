package com.finplanner.finplanner.service.expense;

import com.finplanner.finplanner.dto.expense.CreateExpenseDto;
import com.finplanner.finplanner.dto.expense.ExpenseDto;
import com.finplanner.finplanner.dto.expense.PatchExpenseDto;
import com.finplanner.finplanner.mapper.ExpenseMapper;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.Expense;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.CategoryRepository;
import com.finplanner.finplanner.repository.ExpenseRepository;
import com.finplanner.finplanner.service.common.CategoryBasedEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseServiceImpl extends CategoryBasedEntityService<Expense> implements ExpenseService {
    private final static String ENTITY_NAME = "Expense";
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              ExpenseMapper expenseMapper) {
        super(categoryRepository);
        this.expenseRepository = expenseRepository;
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
        Expense expenseToReplace = findAuthorizedEntity(expenseId, user);
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
        Expense expenseToPatch = findAuthorizedEntity(expenseId, user);

        if (patchExpenseDto.getAmount() != null) {
            expenseToPatch.setAmount(patchExpenseDto.getAmount());
        }
        if (patchExpenseDto.getCategoryId() != null) {
            Category category = resolveAccessibleCategory(patchExpenseDto.getCategoryId(), user);
            expenseToPatch.setCategory(category);
        }
        if (patchExpenseDto.getDate() != null) {
            expenseToPatch.setDate(patchExpenseDto.getDate());
        }
        if (patchExpenseDto.getDescription() != null) {
            expenseToPatch.setDescription(patchExpenseDto.getDescription());
        }
        expenseRepository.save(expenseToPatch);
        return expenseMapper.toExpenseDto(expenseToPatch);
    }

    @Override
    public void deleteExpense(UUID expenseId, User user) {
        Expense expenseToDelete = findAuthorizedEntity(expenseId, user);
        expenseRepository.delete(expenseToDelete);
    }

    @Override
    protected Optional<Expense> findByIdForAdmin(UUID entityId) {
        return expenseRepository.findById(entityId);
    }

    @Override
    protected Optional<Expense> findByIdAndUserId(UUID entityId, UUID userId) {
        return expenseRepository.findByIdAndUserId(entityId, userId);
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

}
