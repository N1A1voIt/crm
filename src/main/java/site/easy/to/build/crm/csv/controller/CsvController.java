package site.easy.to.build.crm.csv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.CsvGenericService;
import site.easy.to.build.crm.csv.CsvService;

import java.util.ArrayList;
import java.util.List;
@Controller
public class CsvController {
    @Autowired
    CsvService csvService;
    @Autowired
    CsvGenericService csvGenericService;
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
    public String csvGenericImport(Model model, @RequestParam("file") MultipartFile file) {
        try{
            List<Exception> exceptions = csvGenericService.importCLG(file);
            if (exceptions != null) {
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
}
