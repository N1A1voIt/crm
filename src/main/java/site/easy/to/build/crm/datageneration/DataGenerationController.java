package site.easy.to.build.crm.datageneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataGenerationController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping("employee/generate-data")
    public String generateData(Model model) {
        try{
            dataGenerationService.generateData();
            model.addAttribute("message", "Data generated");
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
        }
        return "settings/cleanup";
    }
}
