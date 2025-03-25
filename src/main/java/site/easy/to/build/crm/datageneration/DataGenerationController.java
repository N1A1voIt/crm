package site.easy.to.build.crm.datageneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.AuthenticationUtils;

@Controller
public class DataGenerationController {
    @Autowired
    private DataGenerationService dataGenerationService;
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    private UserService userService;
    @GetMapping("employee/generate-data")
    public String generateData(Model model, Authentication auth) {
        try{
            int userId = authenticationUtils.getLoggedInUserId(auth);
            User loggedInUser = userService.findById(userId);
            dataGenerationService.generateAll(loggedInUser);
            model.addAttribute("message", "Data generated");
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
        }
        return "settings/cleanup";
    }
}
