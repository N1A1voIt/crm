package site.easy.to.build.crm.listController;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.repository.VExpensLeadRepository;
import site.easy.to.build.crm.repository.VExpensTicketRepository;
import site.easy.to.build.crm.util.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ListController {
    @Autowired
    VExpensLeadRepository vExpensLeadRepository;
    @Autowired
    VExpensTicketRepository vExpensTicketRepository;

    @GetMapping("/api/list/tickets")
    public ResponseEntity<ApiResponse> listTickets() {
        try{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(200);
            apiResponse.setData(vExpensTicketRepository.findAll());
            apiResponse.setExceptions(null);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            ApiResponse apiResponse = new ApiResponse();
            List<Exception> exceptions = new ArrayList<>();
            apiResponse.setStatus(400);
            apiResponse.setData(null);
            exceptions.add(e);
            apiResponse.setExceptions(exceptions);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/api/list/leads")
    public ResponseEntity<ApiResponse> listLeads() {
        try{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(200);
            apiResponse.setData(vExpensLeadRepository.findAll());
            apiResponse.setExceptions(null);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            ApiResponse apiResponse = new ApiResponse();
            List<Exception> exceptions = new ArrayList<>();
            apiResponse.setStatus(400);
            apiResponse.setData(null);
            exceptions.add(e);
            apiResponse.setExceptions(exceptions);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
