package service;

import model.ExpenseClaim;
import org.springframework.stereotype.Service;
import repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service

    public class ExpenseService {

        private final ExpenseRepository repo;

        public ExpenseService(ExpenseRepository repo) {
            this.repo = repo;
        }

        public Map<String, String> validateExpense(ExpenseClaim claim) {
            Map<String, String> errors = new HashMap<>();


            if (claim.getAmount() <= 0) {
                errors.put("amount", "Amount must be greater than zero");
            }


            if (claim.getAmount() > 1000) {
                errors.put("amount", "Amount exceeds $1000 limit");
            }

            switch (claim.getCategory()) {
                case "Travel":
                    if (claim.getAmount() > 500)
                        errors.put("amount", "Travel max is $500");
                    break;
                case "Meals":
                    if (claim.getAmount() > 100)
                        errors.put("amount", "Meals max is $100");
                    break;
                case "Other":
                    if (claim.getAmount() > 300)
                        errors.put("amount", "Other max is $300");
                    break;
            }

            LocalDate today = LocalDate.now();
            if (claim.getExpenseDate().isAfter(today)) {
                errors.put("date", "Date cannot be in future");
            }
            if (claim.getExpenseDate().isBefore(today.minusDays(30))) {
                errors.put("date", "Expense older than 30 days");
            }

            if (claim.getDescription().trim().isEmpty()) {
                errors.put("description", "Description required");
            }

            return errors;
        }

        public ExpenseClaim saveExpense(ExpenseClaim claim) {
            claim.setStatus("SUBMITTED");
            return repo.save(claim);
        }
    }
