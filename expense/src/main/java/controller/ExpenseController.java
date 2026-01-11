package controller;

import model.ExpenseClaim;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpenseService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:5174")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> submitExpense(@RequestBody ExpenseClaim claim) {

        Map<String, String> errors = service.validateExpense(claim);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(service.saveExpense(claim));
    }

    @GetMapping
    public List<ExpenseClaim> getAll() {
        return Collections.singletonList(service.saveExpense(null));
    }
}
