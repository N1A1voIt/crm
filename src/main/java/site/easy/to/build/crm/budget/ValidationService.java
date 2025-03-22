package site.easy.to.build.crm.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.entity.BudgetCustomer;
import site.easy.to.build.crm.budget.service.BudgetService;
import site.easy.to.build.crm.depenses.entity.TotalExpens;
import site.easy.to.build.crm.depenses.repository.TotalExpensRepository;

@Service
public class ValidationService {
    @Autowired
    TotalExpensRepository totalExpensRepository;
    @Autowired
    BudgetService budgetService;

    public BudgetValidator validateBudget(double amount,int idCustomer) {
        TotalExpens totalExpens = totalExpensRepository.findById(idCustomer).get();
        BudgetCustomer budgetCustomer = budgetService.getBudgetCustomer(idCustomer);
        double total  = totalExpens.getTotal().doubleValue() + amount;
        double budget = budgetCustomer.getBudget().doubleValue();
        BudgetValidator budgetValidator = new BudgetValidator();
        System.out.println(budget);
        if (budget < total) {
            budgetValidator.setType(2);
            budgetValidator.setMessage("Vous avez dépassé votre budget de "+ (total-budget) + " êtes vous sûr de vouloir continuer?");
        }
        else if (budget * 0.5 < total) {
            budgetValidator.setType(1);
            budgetValidator.setMessage("Vous avez dépassé votre budget * taux de ");
        }
        System.out.println("Miditra ato");
        return budgetValidator;
    }
}
