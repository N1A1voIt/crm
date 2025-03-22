package site.easy.to.build.crm.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.easy.to.build.crm.budget.entity.BudgetCustomer;

import java.util.Optional;

public interface BudgetCustomerRepository extends JpaRepository<BudgetCustomer, Integer> {
    Optional<BudgetCustomer> findByCustomerId(Integer customerId);

}
