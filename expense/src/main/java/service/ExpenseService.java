package service;

import dto.ExpenseRequest;
import jakarta.validation.ValidationException;
import model.ExpenseClaim;
import org.springframework.stereotype.Service;
import repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service

    public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public ExpenseClaim createExpense(ExpenseRequest request) {

        Map<String, String> errors = new HashMap<>();


        LocalDate today = LocalDate.now();
        if (request.getExpenseDate().isAfter(today)) {
            errors.put("expenseDate", "Expense date cannot be in the future");
        }
        if (request.getExpenseDate().isBefore(today.minusDays(30))) {
            errors.put("expenseDate", "Expense date must be within last 30 days");
        }


        double amount = request.getAmount();
        if (amount > 1000) {
            errors.put("amount", "Maximum allowed amount is $1000");
        }

        switch (request.getCategory()) {
            case "Travel" -> {
                if (amount > 500)
                    errors.put("amount", "Travel expenses max $500");
            }
            case "Meals" -> {
                if (amount > 100)
                    errors.put("amount", "Meals expenses max $100");
            }
            case "Other" -> {
                if (amount > 300)
                    errors.put("amount", "Other expenses max $300");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException((Throwable) errors);
        }

        ExpenseClaim claim = new ExpenseClaim();
        claim.setExpenseDate(request.getExpenseDate());
        claim.setCategory(request.getCategory());
        claim.setAmount(request.getAmount());
        claim.setDescription(request.getDescription());
        claim.setStatus("SUBMITTED");

        return repository.save(claim);
    }
    }
