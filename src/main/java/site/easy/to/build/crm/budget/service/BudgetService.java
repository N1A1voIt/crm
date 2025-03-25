package site.easy.to.build.crm.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.entity.BudgetCustomer;
import site.easy.to.build.crm.budget.entity.BudgetWithDetails;
import site.easy.to.build.crm.budget.repository.BudgetCustomerRepository;
import site.easy.to.build.crm.budget.repository.BudgetRepository;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.repository.CustomerRepository;

import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BudgetCustomerRepository budgetCustomerRepository;

    public BudgetCustomer getBudgetCustomer(int idCustomer) {
        BudgetCustomer budgetCustomer = budgetCustomerRepository.findByCustomerId(idCustomer).orElse(null);
        return budgetCustomer;
    }

    public BudgetWithDetails getBudgetCpl(int idCustomer) {
        BudgetCustomer budgetCustomer = getBudgetCustomer(idCustomer);
        List<Budget> budgets = budgetRepository.findBudgetByCustomer(idCustomer);
        BudgetWithDetails budgetWithDetails = new BudgetWithDetails();
        budgetWithDetails.setBudgets(budgets);
        budgetWithDetails.setCustomer(budgetCustomer);
        return budgetWithDetails;
    }

}
