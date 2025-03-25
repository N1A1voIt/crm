package site.easy.to.build.crm.dashboardController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.service.customer.CustomerServiceImpl;
import site.easy.to.build.crm.service.lead.LeadServiceImpl;
import site.easy.to.build.crm.service.ticket.TicketServiceImpl;
import site.easy.to.build.crm.util.ApiResponse;

@RestController
//@RequestMapping(name = "/api")
public class DashboardController {
    @Autowired
    LeadServiceImpl leadService;
    @Autowired
    TicketServiceImpl ticketService;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    TotalService totalService;

    @GetMapping("/api/dashboard")
    public ResponseEntity<ApiResponse> dashboard() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            DashboardItem dashboardItem = new DashboardItem();
            dashboardItem.setCustomerBudget(customerService.budgetCustomer());
            dashboardItem.setLeadsByEmploye(leadService.leadsByEmployee());
            dashboardItem.setTotal(totalService.getTotal());
            apiResponse.setData(dashboardItem);
            apiResponse.setStatus(200);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(new ApiResponse());
    }

    @GetMapping("/api/dashboard/details/budget")
    public ResponseEntity<ApiResponse> budgetDetails() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData((customerService.budgets()));
            apiResponse.setStatus(200);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(new ApiResponse());
    }
    @GetMapping("/api/dashboard/details/leads")
    public ResponseEntity<ApiResponse> leadsDetails() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData((leadService.findAll()));
            apiResponse.setStatus(200);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(new ApiResponse());
    }
    @GetMapping("/api/dashboard/details/tickets")
    public ResponseEntity<ApiResponse> ticketsDetails() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData((ticketService.findAll()));
            apiResponse.setStatus(200);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(new ApiResponse());
    }
}
