package site.easy.to.build.crm.depenses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.BudgetValidator;
import site.easy.to.build.crm.budget.entity.BudgetCustomer;
import site.easy.to.build.crm.budget.service.BudgetService;
import site.easy.to.build.crm.depenses.entity.Expens;
import site.easy.to.build.crm.depenses.entity.TotalExpens;
import site.easy.to.build.crm.depenses.repository.ExpensesRepository;
import site.easy.to.build.crm.depenses.repository.TotalExpensRepository;

@Service
public class ExpensService {
    @Autowired
    ExpensesRepository expensesRepository;
    @Autowired
    TotalExpensRepository totalExpensRepository;
    @Autowired
    BudgetService budgetService;

    public Expens save(Expens expenses) {
        if (expenses.getTickets() != null) {
            if (expensesRepository.findByTicketsCustomerCustomerId(expenses.getTickets().getCustomer().getCustomerId()).isPresent()) {
                expenses = expensesRepository.findByTicketsCustomerCustomerId(expenses.getTickets().getCustomer().getCustomerId()).get();

            }
        }
        if (expenses.getLeads() != null) {
            if (expensesRepository.findByLeadsCustomerCustomerId(expenses.getLeads().getCustomer().getCustomerId()).isPresent()) {
                expenses = expensesRepository.findByLeadsCustomerCustomerId(expenses.getLeads().getCustomer().getCustomerId()).get();

            }
        }
        return expensesRepository.save(expenses);
    }
    public Expens findByTicketsId(Integer ticketsId) {
         return expensesRepository.findByTicket(ticketsId).orElse(null);
    }
    public Expens findByLeadsId(Integer leadsId) {
        return expensesRepository.findByLead(leadsId).orElse(null);
    }

}
