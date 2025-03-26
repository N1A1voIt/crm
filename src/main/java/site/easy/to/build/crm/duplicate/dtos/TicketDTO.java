package site.easy.to.build.crm.duplicate.dtos;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Ticket;

import java.time.LocalDateTime;
@Getter
@Setter
public class TicketDTO {

    private Integer ticketId;
    private String subject;
    private String description;
    private String status;
    private String priority;
    private UserDTO manager;
    private UserDTO employee;
    private CustomerDTO customer;
    private LocalDateTime createdAt;
    private Double depense;

    public TicketDTO(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.subject = ticket.getSubject();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.priority = ticket.getPriority();
        this.createdAt = ticket.getCreatedAt();
        this.depense = ticket.getDepense();

        if (ticket.getManager() != null) {
            this.manager = new UserDTO(ticket.getManager());
        }
        if (ticket.getEmployee() != null) {
            this.employee = new UserDTO(ticket.getEmployee());
        }
        if (ticket.getCustomer() != null) {
            this.customer = new CustomerDTO(ticket.getCustomer());
        }
    }

}
