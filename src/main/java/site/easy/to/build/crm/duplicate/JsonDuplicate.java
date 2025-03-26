package site.easy.to.build.crm.duplicate;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.budget.ObjectMappedBudget;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

import java.util.List;

@Getter
@Setter
public class JsonDuplicate {
    Customer customer;
    List<Ticket> tickets;
    List<Lead> leads;
    List<ObjectMappedBudget> budgets;
}
