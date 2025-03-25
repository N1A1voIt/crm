package site.easy.to.build.crm.csv.ticketLead;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.datageneration.DataGenerationService;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Service
public class TicketLeadService {
    private Faker faker = new Faker();
    @Autowired
    DataGenerationService dataGenerationService;
    public Object generateTicketOrLead(TicketLeadImp ticketLead,TicketLeadArgs ticketLeadArgs) {
        if(ticketLead.isTicket()) {
            return toTicket(ticketLead,ticketLeadArgs);
        }
        return toLead(ticketLead,ticketLeadArgs);
    }
    private Ticket toTicket(TicketLeadImp ticketLead,TicketLeadArgs ticketLeadArgs) {
        Ticket ticket = new Ticket();
        ticket.setSubject(ticketLead.getSubjectOrName());
        ticket.setStatus(ticketLead.getStatus());
        ticket.setDepense(ticketLead.getExpense());
        ticket.setDescription(faker.leagueOfLegends().quote());
        ticket.setPriority(ticketLeadArgs.getPriorities()[faker.number().numberBetween(0,ticketLeadArgs.getPriorities().length-1)]);
        ticket.setManager(ticketLeadArgs.getUser());
        ticket.setEmployee(ticketLeadArgs.getEmployee().get(faker.number().numberBetween(0,ticketLeadArgs.getEmployee().size()-1)));
        ticket.setCustomer(ticketLeadArgs.getCustomers().get(ticketLead.getCustomerEmail()));
        ticket.setCreatedAt(dataGenerationService.generateRandomDate());
        return ticket;
    }
    private Lead toLead(TicketLeadImp ticketLead, TicketLeadArgs ticketLeadArgs) {
        Lead lead = new Lead();
        lead.setName(ticketLead.getSubjectOrName());
        lead.setStatus(ticketLead.getStatus());
        lead.setDepense(ticketLead.getExpense());
        lead.setManager(ticketLeadArgs.getUser());
        lead.setEmployee(ticketLeadArgs.getEmployee().get(faker.number().numberBetween(0,ticketLeadArgs.getEmployee().size()-1)));
        lead.setCustomer(ticketLeadArgs.getCustomers().get(ticketLead.getCustomerEmail()));
        lead.setCreatedAt(dataGenerationService.generateRandomDate());
        return lead;
    }
}
