package site.easy.to.build.crm.restTicketLead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.depenses.entity.Expens;
import site.easy.to.build.crm.depenses.repository.ExpensesRepository;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.service.lead.LeadServiceImpl;
import site.easy.to.build.crm.service.ticket.TicketServiceImpl;

import java.math.BigDecimal;

@Service
public class TLService {
    @Autowired
    TicketServiceImpl ticketServiceImpl;
    @Autowired
    LeadServiceImpl leadService;
    @Autowired
    ExpensesRepository expensesRepository;

    public Lead updateLead(TLBody tlBody){
        Lead lead = leadService.findByLeadId(tlBody.getId());
        lead.setDepense(tlBody.amount);
        return leadService.save(lead);
    }
    public Ticket updateTicket(TLBody tlBody) throws Exception{
        Ticket ticket = ticketServiceImpl.findByTicketId(tlBody.getId());
        ticket.setDepense(tlBody.amount);
        return ticketServiceImpl.save(ticket);
    }
}
