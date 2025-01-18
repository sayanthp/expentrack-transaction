package com.saytech.expentrack.transactionservice.service;

import com.saytech.expentrack.transactionservice.dto.TransactionDTO;
import com.saytech.expentrack.transactionservice.entity.Budget;
import com.saytech.expentrack.transactionservice.entity.Transaction;
import com.saytech.expentrack.transactionservice.enums.CategoryType;
import com.saytech.expentrack.transactionservice.enums.TransactionType;
import com.saytech.expentrack.transactionservice.event.BudgetExceededEvent;
import com.saytech.expentrack.transactionservice.event.TransactionCreatedEvent;
import com.saytech.expentrack.transactionservice.exception.ResourceNotFoundException;
import com.saytech.expentrack.transactionservice.repository.TransactionRepository;
import com.saytech.expentrack.transactionservice.utility.GenericConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private GenericConverter genericConverter;

    @Autowired
    private BudgetService budgetService;


    public List<TransactionDTO> getAllTransactionsForUser(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("transactions for user", userId);
        }
        return genericConverter.convertEntitiesToDtos(transactions, TransactionDTO.class);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = genericConverter.convertDtoToEntity(transactionDTO,Transaction.class);
        Transaction createdTransaction = transactionRepository.save(transaction);

        // Publish an event after saving the expense
        TransactionCreatedEvent transactionCreatedEvent = new TransactionCreatedEvent(createdTransaction);
        eventPublisher.publishEvent(transactionCreatedEvent);

        //if this transaction exceeds the budget, publish budget exceeded event
        checkAndPublishBudgetExceededEvent(transaction);

        return genericConverter.convertEntityToDto(createdTransaction, TransactionDTO.class);
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = findTransactionById(id);
        return genericConverter.convertEntityToDto(transaction, TransactionDTO.class);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        findTransactionById(id);
        Transaction transaction = genericConverter.convertDtoToEntity(transactionDTO,Transaction.class);
        transaction.setId(id);
        return genericConverter.convertEntityToDto(transactionRepository.save(transaction), TransactionDTO.class);
    }

    public boolean deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction", id);
        }
        transactionRepository.deleteById(id);
        return true;
    }

    private Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }


    private double getCategoryTotalExpenses(Long userId, CategoryType category) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory(userId, category.toString());
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.DEBIT)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private void checkAndPublishBudgetExceededEvent(Transaction transaction) {

        Long userId = transaction.getUserId();
        CategoryType category = transaction.getCategory();

        double currentTotal = getCategoryTotalExpenses(userId,category);
        Budget budget = budgetService.getBudgetByUserIdAndCategory(userId,category);

        double exceededAmt = (currentTotal + transaction.getAmount()) - budget.getAmount();

        if (exceededAmt > 0){
            BudgetExceededEvent budgetExceededEvent = new BudgetExceededEvent(budget,exceededAmt);
            eventPublisher.publishEvent(budgetExceededEvent);
        }

    }


}
