package site.easy.to.build.crm.csv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.CSVProcessingException;
import site.easy.to.build.crm.csv.CsvGenericService;
import site.easy.to.build.crm.csv.CsvService;
import site.easy.to.build.crm.csv.customerTemp.ImportCustomer;
import site.easy.to.build.crm.csv.ticket.ImportTicket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;
@Controller
public class CsvController {
    @Autowired
    CsvService csvService;
    @Autowired
    CsvGenericService csvGenericService;
    @Autowired
    ImportCustomer importCustomer;
    @Autowired
    ImportTicket importTicket;
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationUtils authenticationUtils;

    @GetMapping("/csv-page")
    public String csvPage(Model model) {
        return "/csv-page";
    }

    @PostMapping("/employee/csv-import")
    public String csvImport(Model model, @RequestParam("file") MultipartFile file) {
        try{
            List<Exception> exceptions = csvService.importCSV(file);
            if (exceptions != null) {
                System.out.println("srdtcvybjnk,lerbrbr");
                List<String> messages = new ArrayList<>();
                for (Exception exception : exceptions) {
                    messages.add(exception.getMessage());
                }
                model.addAttribute("error", messages);
            }
            return "redirect:/csv-page";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/csv-page";
    }
    @PostMapping("/employee/csv-generic-import")
    public String csvGenericImport(Model model, @RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2,@RequestParam("file3") MultipartFile file3, Authentication authentication) {
        try{
            int userId = authenticationUtils.getLoggedInUserId(authentication);
            User user = userService.findById(userId);
            List<String> exceptions = csvGenericService.importCPL(file,file2,file3,user);
            return "/csv-page";
        } catch (CSVProcessingException e) {
            List<String> messages = new ArrayList<>();
            for (String exception : e.getErrors()) {
                System.out.println("dztfyudiaz"+exception);
                messages.add(exception);
            }
            model.addAttribute("error", e.getErrors());
            e.printStackTrace();
            return "/csv-page";
        }
    }
}
