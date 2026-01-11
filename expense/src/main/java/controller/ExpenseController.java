package controller;

import dto.ExpenseRequest;
import jakarta.validation.Valid;
import model.ExpenseClaim;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpenseService;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExpenseClaim> submitExpense(
            @Valid @RequestBody ExpenseRequest request) {

        ExpenseClaim claim = service.createExpense(request);
        return ResponseEntity.ok(claim);
    }
}
