package site.easy.to.build.crm.restTicketLead;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.depenses.repository.ExpensesRepository;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.repository.LeadRepository;
import site.easy.to.build.crm.repository.TicketRepository;
import site.easy.to.build.crm.service.lead.LeadServiceImpl;
import site.easy.to.build.crm.service.ticket.TicketServiceImpl;
import site.easy.to.build.crm.util.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketLeadRestController {
    @Autowired
    TLService tlService;
    @Autowired
    LeadServiceImpl leadService;
    @Autowired
    TicketServiceImpl ticketService;
    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    LeadRepository leadRepository;
    @Autowired
    TicketRepository ticketRepository;

    @PostMapping("/api/ticket/update")
    public ResponseEntity<ApiResponse> updateTicket(@RequestBody TLBody ticket) {
        ApiResponse response = new ApiResponse();
        try{
            response.setStatus(200);
            tlService.updateTicket(ticket);
            response.setData("Update successful");
            response.setExceptions(null);
        }catch (Exception e){
            List<Exception> exceptions = new ArrayList<>();
            exceptions.add(e);
            response.setStatus(400);
            response.setData(null);
            response.setExceptions(exceptions);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
    @PostMapping("/api/lead/update")
    public ResponseEntity<ApiResponse> updateLead(@RequestBody TLBody lead) {
        ApiResponse response = new ApiResponse();
        try{
            response.setStatus(200);
            tlService.updateLead(lead);
            response.setData("Update successful");
            response.setExceptions(null);
        }catch (Exception e){
            List<Exception> exceptions = new ArrayList<>();
            exceptions.add(e);
            response.setStatus(400);
            response.setData(null);
            e.printStackTrace();
            response.setExceptions(exceptions);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @DeleteMapping("/api/lead/delete/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteLead(@PathVariable int id) {
        ApiResponse response = new ApiResponse();
        try{
            response.setStatus(200);
//            expensesRepository.deleteByLead(id);
            leadRepository.deleteById(id);
            response.setData("Delete successful");
            response.setExceptions(null);
        }catch (Exception e){
            List<Exception> exceptions = new ArrayList<>();
            exceptions.add(e);
            response.setStatus(400);
            response.setData(null);
            e.printStackTrace();
            response.setExceptions(exceptions);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
    @DeleteMapping("/api/ticket/delete/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteTicket(@PathVariable int id) {
        ApiResponse response = new ApiResponse();
        try{
            response.setStatus(200);
//            expensesRepository.deleteById();
//            expensesRepository.deleteByTicket(id);
            ticketRepository.deleteById(id);
            response.setData("Delete successful");
            response.setExceptions(null);
        }catch (Exception e){
            List<Exception> exceptions = new ArrayList<>();
            exceptions.add(e);
            response.setStatus(400);
            response.setData(null);
            e.printStackTrace();
            response.setExceptions(exceptions);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

}
