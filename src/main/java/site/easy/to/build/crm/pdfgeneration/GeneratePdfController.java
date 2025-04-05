package site.easy.to.build.crm.pdfgeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.repository.CustomerLoginInfoRepository;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoServiceImpl;

@Controller
public class GeneratePdfController {
    @Autowired
    GeneratePdf<CustomerLoginInfo> generatePdf;
    @Autowired
    CustomerLoginInfoRepository customerLoginInfoService;

    @GetMapping("/employee/customer-login-pdf")
    public String employeeLogin() {
        generatePdf.exportToPDF(customerLoginInfoService.findAll(),"customer-login.pdf");
        return "settings/cleanup";
    }
}
