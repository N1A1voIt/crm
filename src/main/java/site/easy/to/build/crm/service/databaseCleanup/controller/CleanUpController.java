package site.easy.to.build.crm.service.databaseCleanup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.easy.to.build.crm.service.databaseCleanup.CleanUpService;

@Controller
public class CleanUpController {
    @Autowired
    CleanUpService cleanUpService;
    @GetMapping("/cleanup")
    public String cleanUp() {
        cleanUpService.cleanupDatabase();
        System.out.println("Cleaned");
        return "settings/cleanup";
    }
    @GetMapping("/cleanup-page")
    public String cleanUpPage() {
        return "settings/cleanup";
    }
}
