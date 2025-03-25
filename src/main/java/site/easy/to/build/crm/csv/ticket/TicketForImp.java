package site.easy.to.build.crm.csv.ticket;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trigger_ticket")
@Getter
@Setter
public class TicketForImp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "subject")
    @CsvBindByName(column = "subject")
    @NotBlank(message = "Subject is required")
    private String subject;

    @Column(name = "description")
    @CsvBindByName(column = "description")
    private String description;

    @Column(name = "status")
    @CsvBindByName(column = "status")
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived)$", message = "Invalid status")
    private String status;

    @CsvBindByName(column = "priority")
    @Column(name = "priority")
    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "^(low|medium|high|closed|urgent|critical)$", message = "Invalid priority")
    private String priority;


    @Column(name = "manager_id")
    @CsvBindByName(column = "manager_id")
    private Integer manager;


    @Column(name = "employee_id")
    @CsvBindByName(column = "employee_id")
    private Integer employee;


    @Column(name = "customer_id")
    @CsvBindByName(column = "customer_id")
    private Integer customer;


    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "depense")
    @CsvBindByName(column = "depense")
    private Double depense;
}
