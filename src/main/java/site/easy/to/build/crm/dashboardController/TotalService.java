package site.easy.to.build.crm.dashboardController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.depenses.entity.TotaExpensLead;
import site.easy.to.build.crm.depenses.entity.TotaExpensTicket;
import site.easy.to.build.crm.depenses.entity.TotalExpens;
import site.easy.to.build.crm.depenses.repository.TotalExpensTicketRepository;
import site.easy.to.build.crm.depenses.repository.TotalExpenseLeadRepository;

import java.util.List;

@Service
public class TotalService {
    @Autowired
    TotalExpenseLeadRepository totalExpenseLeadRepository;
    @Autowired
    TotalExpensTicketRepository totalExpensTicketRepository;

    public Total getTotal() {
        Total total = new Total();
        double a = 0;
        double b = 0;
        List<TotaExpensTicket> totalExpensTicket = totalExpensTicketRepository.findAll();
        for (TotaExpensTicket ticket : totalExpensTicket) {a+=ticket.getTicketExpense().doubleValue();}
        List<TotaExpensLead> totalExpensLead = totalExpenseLeadRepository.findAll();
        for (TotaExpensLead ticket : totalExpensLead) {b+=ticket.getLeadExpense().doubleValue();}
        total.setTotalTickets(a);
        total.setTotalLeads(b);
        return total;
    }
}
