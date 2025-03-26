package site.easy.to.build.crm.budget.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.repository.BudgetRepository;
import site.easy.to.build.crm.budget.service.BudgetService;

@Controller
public class BudgetController {
    @Autowired
    BudgetRepository budgetRepository;
    @Autowired
    BudgetService budgetService;
    @GetMapping("/employee/budget/{idCustomer}")
    public String budget(Model model, @PathVariable Integer idCustomer) {
        Budget budget = new Budget();
        budget.setCustomer(idCustomer);
        model.addAttribute("budget", budget);
        return "budget/budget-save";
    }

    @PostMapping("/employee/budget/save")
    public String save(@ModelAttribute("budget") @Valid Budget budget, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("hcjnecec:Error");
            model.addAttribute("budget", budget);
            return "budget/budget-save";
        }
        budgetRepository.save(budget);
        return "redirect:/employee/customer/" + budget.getCustomer();
    }


    @GetMapping(name = "/employee/budget/details/{idCustomer}")
    public String details(Model model, @PathVariable(name = "idCustomer") int idCustomer) {
          model.addAttribute("budget",budgetService.getBudgetCpl(idCustomer));
          return "budget/details";
    }
}
