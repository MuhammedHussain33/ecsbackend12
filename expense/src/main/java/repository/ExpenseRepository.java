package repository;

import model.ExpenseClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseClaim,Integer> {
}
