package site.easy.to.build.crm.ticketDashController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.service.ticket.TicketServiceImpl;
import site.easy.to.build.crm.util.ApiResponse;

@RestController
//@RequestMapping(name = "/api/test/")
public class TicketDashboardController {
    @Autowired
    private TicketServiceImpl ticketService;
    @GetMapping("/api/cost-per-tickets")
    public ResponseEntity<?> costPerTickets(@RequestParam String frequency) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(200);
            apiResponse.setData(ticketService.findCostPerTickets(frequency));
            apiResponse.setExceptions(null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
