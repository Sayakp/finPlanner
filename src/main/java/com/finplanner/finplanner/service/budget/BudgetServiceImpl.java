package com.finplanner.finplanner.service.budget;

import com.finplanner.finplanner.dto.budget.BudgetDto;
import com.finplanner.finplanner.dto.budget.CreateBudgetDto;
import com.finplanner.finplanner.dto.budget.PatchBudgetDto;
import com.finplanner.finplanner.dto.budget.UpdateBudgetDto;
import com.finplanner.finplanner.mapper.BudgetMapper;
import com.finplanner.finplanner.model.Budget;
import com.finplanner.finplanner.model.Category;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.repository.BudgetRepository;
import com.finplanner.finplanner.repository.CategoryRepository;
import com.finplanner.finplanner.service.common.CategoryBasedEntityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetServiceImpl extends CategoryBasedEntityService<Budget> implements BudgetService {
    private final static String ENTITY_NAME = "Budget";
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetRepository budgetRepository,
                             BudgetMapper budgetMapper,
                             CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Override
    public List<BudgetDto> getUserBudgets(User user) {
        List<Budget> budgets = budgetRepository.findAllByUserId(user.getId());
        return budgetMapper.toBudgetDtoList(budgets);
    }

    @Override
    public BudgetDto getBudgetById(UUID budgetId, User user) {
        Budget budget = findAuthorizedEntity(budgetId, user);
        return budgetMapper.toBudgetDto(budget);
    }

    @Override
    public BudgetDto createBudget(CreateBudgetDto budgetDto, User user) {
        Budget budget = budgetMapper.toBudget(budgetDto, user);
        Category category = resolveAccessibleCategory(budgetDto.getCategoryId(), user);
        budget.setCategory(category);
        budgetRepository.save(budget);
        return budgetMapper.toBudgetDto(budget);
    }

    @Override
    @Transactional
    public BudgetDto replaceBudget(UUID budgetId, UpdateBudgetDto budgetDto, User user) {
        Budget budgetToReplace = findAuthorizedEntity(budgetId, user);
        budgetMapper.updateBudgetFromUpdateDto(budgetDto, budgetToReplace);
        Category category = resolveAccessibleCategory(budgetDto.getCategoryId(), user);
        budgetToReplace.setCategory(category);
        return budgetMapper.toBudgetDto(budgetRepository.save(budgetToReplace));
    }

    @Override
    @Transactional
    public BudgetDto patchBudget(UUID budgetId, PatchBudgetDto budgetDto, User user) {
        Budget budgetToPatch = findAuthorizedEntity(budgetId, user);
        budgetMapper.updateBudgetFromPatchDto(budgetDto, budgetToPatch);

        if (budgetDto.getCategoryId() != null) {
            Category category = resolveAccessibleCategory(budgetDto.getCategoryId(), user);
            budgetToPatch.setCategory(category);
        }

        return budgetMapper.toBudgetDto(budgetRepository.save(budgetToPatch));
    }

    @Override
    public void deleteBudget(UUID budgetId, User user) {
        Budget budgetToDelete = findAuthorizedEntity(budgetId, user);
        budgetRepository.delete(budgetToDelete);
    }

    @Override
    protected Optional<Budget> findByIdForAdmin(UUID entityId) {
        return budgetRepository.findById(entityId);
    }

    @Override
    protected Optional<Budget> findByIdAndUserId(UUID entityId, UUID userId) {
        return budgetRepository.findByIdAndUserId(entityId, userId);
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }
}
