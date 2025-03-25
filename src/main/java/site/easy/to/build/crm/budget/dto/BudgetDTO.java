package site.easy.to.build.crm.budget.dto;

import site.easy.to.build.crm.budget.entity.Budget;

import java.math.BigDecimal;

public class BudgetDTO {
    private Long budgetId;
    private String designation;
    private String customerName;
    private BigDecimal budget;
    public BudgetDTO(Budget budget) {
        this.budgetId = Long.valueOf(budget.getId());
        this.customerName = budget.getCustomere().getName(); // Only necessary fields
        this.designation = budget.getDesignation();
        this.budget = budget.getBudget();
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}

