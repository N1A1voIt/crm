package site.easy.to.build.crm.depenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import site.easy.to.build.crm.depenses.entity.Expens;
import site.easy.to.build.crm.depenses.repository.ExpensesRepository;
import site.easy.to.build.crm.depenses.service.ExpensService;

@Controller
public class ExpensController {
    @Autowired
    ExpensService expensesService;

    @PostMapping("/employee/expens/save")
    public String postExpens(@ModelAttribute("expens")Expens expens, Model model){
        expensesService.save(expens);
        return "redirect:/employee/expens";
    }



}
