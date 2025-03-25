package site.easy.to.build.crm.csv.ticketLead;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ticket_lead_imp")
public class TicketLeadImp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_email", nullable = false, length = 250)
    @CsvBindByName(column = "customer_email")
    private String customerEmail;

    @Size(max = 250)
    @Column(name = "subject_or_name", length = 250)
    @CsvBindByName(column = "subject_or_name")
    private String subjectOrName;

    @Size(max = 250)
    @NotNull
    @Column(name = "type", nullable = false, length = 250)
    @Pattern(regexp = "^(ticket|lead)",message = "Needs to be a ticket or a lead")
    @CsvBindByName(column = "type")
    private String type;

    @Size(max = 250)
    @Column(name = "status", length = 250)
    @CsvBindByName(column = "status")
    @Pattern(regexp = "^(meeting-to-schedule|scheduled|archived|success|assign-to-sales|open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated)$", message = "Invalid status")
    private String status;

    @NotNull
    @Column(name = "expense", nullable = false, precision = 18, scale = 2)
    @CsvNumber("#,##")
    @CsvBindByName(column = "expense")
    private Double expense;


    public boolean isTicket(){
        return "ticket".equals(type);
    }
}