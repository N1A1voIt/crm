package site.easy.to.build.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.repository.BudgetRepository;
import site.easy.to.build.crm.repository.CustomerRepository;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByCustomerId(int customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findByUserId(int userId) {
        return customerRepository.findByUserId(userId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getRecentCustomers(int userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return customerRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public long countByUserId(int userId) {
        return customerRepository.countByUserId(userId);
    }

    @Override
    public List<CustomerBudgetDTO> budgetCustomer() {
        Map<Integer, BigDecimal> map = new HashMap<>();
        Map<Integer, CustomerDTO> customerMap = new HashMap<>();
        List<Budget> budgets = budgetRepository.findAll();

        for (Budget budget : budgets) {
            Customer customerEntity = budget.getCustomere();
            customerMap.computeIfAbsent(customerEntity.getCustomerId(), k -> {
                CustomerDTO dto = new CustomerDTO();
                dto.setCustomerId(customerEntity.getCustomerId());
                dto.setCustomerName(customerEntity.getName());
                return dto;
            });

            map.put(customerEntity.getCustomerId(),
                    map.getOrDefault(customerEntity.getCustomerId(), BigDecimal.ZERO)
                            .add(budget.getBudget()));
        }

        List<CustomerBudgetDTO> customerBudgets = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            CustomerBudgetDTO customerBudget = new CustomerBudgetDTO();
            customerBudget.setBudget(entry.getValue());
            customerBudget.setCustomer(customerMap.get(entry.getKey()));
            customerBudgets.add(customerBudget);
        }

        return customerBudgets;
    }

    public List<Budget> budgets() {
        return budgetRepository.findAll();
    }

}
