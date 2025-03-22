package site.easy.to.build.crm.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easy.to.build.crm.budget.entity.Budget;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findBudgetByCustomer(Integer customerId);
}
