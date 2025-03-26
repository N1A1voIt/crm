package site.easy.to.build.crm.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easy.to.build.crm.budget.ObjectMappedBudget;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.entity.Customer;

import java.util.List;

public interface ObjectMappedBudgetRepository extends JpaRepository<ObjectMappedBudget, Long> {
    List<ObjectMappedBudget> findByCustomer(Customer customere);
}
