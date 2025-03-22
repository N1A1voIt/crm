package site.easy.to.build.crm.budget.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BudgetWithDetails {
    BudgetCustomer customer;
    List<Budget> budgets;
}
