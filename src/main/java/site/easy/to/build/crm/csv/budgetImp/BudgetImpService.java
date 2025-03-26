package site.easy.to.build.crm.csv.budgetImp;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadArgs;
import site.easy.to.build.crm.datageneration.DataGenerationService;

import java.util.List;

@Service
public class BudgetImpService {
    @Autowired
    DataGenerationService dataGenerationService;
    private Faker faker = new Faker();
    public Budget getBudgets(BudgetImp budgetImp, TicketLeadArgs ticketLeadArgs) {
        Budget budget = new Budget();
        if(ticketLeadArgs.getCustomers().get(budgetImp.getCustomerEmail()) == null) {
            throw new RuntimeException("Customer not found (email)");
        }
        budget.setCustomer(ticketLeadArgs.getCustomers().get(budgetImp.getCustomerEmail()).getCustomerId());
        budget.setBudget(budgetImp.getBudget());
        budget.setDesignation(faker.lorem().word());
        return budget;
    }
}
