package com.saytech.expentrack.transactionservice.service;

import com.saytech.expentrack.transactionservice.dto.BudgetDTO;
import com.saytech.expentrack.transactionservice.enums.CategoryType;
import com.saytech.expentrack.transactionservice.exception.ResourceNotFoundException;
import com.saytech.expentrack.transactionservice.entity.Budget;
import com.saytech.expentrack.transactionservice.repository.BudgetRepository;
import com.saytech.expentrack.transactionservice.utility.GenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing user budgets. This class handles the creation,
 * updating, and retrieval of budget information while ensuring that each user
 * can only have one budget per category. When duplicate categories are encountered,
 * the service updates the existing budget rather than creating a new one.
 */
@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private GenericConverter genericConverter;

    /**
     * Retrieves a specific budget by its ID.
     *
     * @param id The unique identifier of the budget
     * @return The budget data transfer object
     * @throws ResourceNotFoundException if the budget is not found
     */
    public BudgetDTO getBudgetById(Long id) {
        Budget budget = findBudgetById(id);
        return genericConverter.convertEntityToDto(budget, BudgetDTO.class);
    }

    /**
     * Finds a budget for a specific user and category combination.
     *
     * @param userId The user's unique identifier
     * @param category The budget category type
     * @return The matching budget entity
     * @throws ResourceNotFoundException if no matching budget is found
     */
    public Budget getBudgetByUserIdAndCategory(Long userId, CategoryType category) {
        return budgetRepository.findByUserIdAndCategory(userId, category)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Budget not found for user %d and category %s", userId, category)
                ));
    }

    /**
     * Creates or updates multiple budgets. If a budget already exists for a user-category
     * combination, it updates the existing budget instead of creating a new one.
     * This method ensures that each user has only one budget per category by keeping
     * the most recent budget amount for each user-category combination.
     *
     * @param budgetDTOs List of budget DTOs to process
     * @return List of processed budget DTOs
     */
    @Transactional
    public List<BudgetDTO> createBudget(List<BudgetDTO> budgetDTOs) {
        if (budgetDTOs == null || budgetDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        // Group budgets by user-category combination, keeping only the latest entry
        Map<String, BudgetDTO> latestBudgets = budgetDTOs.stream()
                .collect(Collectors.toMap(
                        dto -> dto.getUserId() + "-" + dto.getCategory(),
                        dto -> dto,
                        (existing, replacement) -> replacement
                ));

        List<Budget> budgetsToSave = new ArrayList<>();

        // Process each unique user-category combination
        for (BudgetDTO budgetDTO : latestBudgets.values()) {
            Optional<Budget> existingBudget = budgetRepository
                    .findByUserIdAndCategory(budgetDTO.getUserId(),
                            budgetDTO.getCategory());

            if (existingBudget.isPresent()) {
                // Update existing budget with new amount
                Budget budget = existingBudget.get();
                budget.setAmount(budgetDTO.getAmount());
                budgetsToSave.add(budget);
            } else {
                // Create new budget
                Budget newBudget = genericConverter.convertDtoToEntity(budgetDTO, Budget.class);
                budgetsToSave.add(newBudget);
            }
        }

        // Save all budgets in a single transaction
        List<Budget> savedBudgets = budgetRepository.saveAll(budgetsToSave);

        return savedBudgets.stream()
                .map(budget -> genericConverter.convertEntityToDto(budget, BudgetDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Updates a single budget. If the update involves changing the category and
     * a budget already exists in the target category, it updates the existing
     * budget and removes the old one.
     *
     * @param id The ID of the budget to update
     * @param budgetDTO The updated budget information
     * @return The updated budget DTO
     * @throws ResourceNotFoundException if the budget to update is not found
     */
    @Transactional
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        Budget existingBudget = findBudgetById(id);

        // Handle category change scenarios
        if (!existingBudget.getCategory().equals(budgetDTO.getCategory())) {
            Optional<Budget> existingCategoryBudget = budgetRepository
                    .findByUserIdAndCategory(budgetDTO.getUserId(),
                            budgetDTO.getCategory());

            if (existingCategoryBudget.isPresent()) {
                // Update existing budget in the target category
                Budget budgetToUpdate = existingCategoryBudget.get();
                budgetToUpdate.setAmount(budgetDTO.getAmount());
                budgetRepository.deleteById(id); // Remove the old budget
                return genericConverter.convertEntityToDto(
                        budgetRepository.save(budgetToUpdate),
                        BudgetDTO.class
                );
            }
        }

        // Perform normal update when no category conflict exists
        Budget updatedBudget = genericConverter.convertDtoToEntity(budgetDTO, Budget.class);
        updatedBudget.setId(existingBudget.getId());
        return genericConverter.convertEntityToDto(
                budgetRepository.save(updatedBudget),
                BudgetDTO.class
        );
    }

    /**
     * Deletes a budget by its ID.
     *
     * @param id The ID of the budget to delete
     * @return true if the deletion was successful
     * @throws ResourceNotFoundException if the budget is not found
     */
    @Transactional
    public boolean deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget", id);
        }
        budgetRepository.deleteById(id);
        return true;
    }

    /**
     * Retrieves all budgets for a specific user.
     *
     * @param userId The user's unique identifier
     * @return List of budget DTOs belonging to the user
     * @throws ResourceNotFoundException if no budgets are found for the user
     */
    public List<BudgetDTO> getBudgetsByUser(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        if (budgets.isEmpty()) {
            throw new ResourceNotFoundException("No budgets found for user: " + userId);
        }
        return genericConverter.convertEntitiesToDtos(budgets, BudgetDTO.class);
    }

    /**
     * Helper method to find a budget by its ID.
     *
     * @param id The budget ID to search for
     * @return The found budget entity
     * @throws ResourceNotFoundException if the budget is not found
     */
    private Budget findBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", id));
    }
}