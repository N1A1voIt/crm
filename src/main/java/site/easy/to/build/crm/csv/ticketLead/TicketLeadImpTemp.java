package site.easy.to.build.crm.csv.ticketLead;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import com.opencsv.bean.CsvNumbers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.InvalidRowException;
import site.easy.to.build.crm.csv.MultiPatternNumberConverter;
import site.easy.to.build.crm.csv.Validatable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "temp_ticket_lead_imp")
public class TicketLeadImpTemp implements Validatable {
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
//    @CsvNumbers(value = {})
//    @CsvNumbers({
//            @CsvNumber(value = "#,##0.00", profiles = "profile1"),
//            @CsvNumber(value = "#.##0,00", profiles = "profile2")
//    })
    @CsvBindByName(column = "expense")
    private Double expense;

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }
    public void setExpense(String expense) {
        try{
            this.expense = Double.parseDouble(expense);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isInvalid() throws InvalidRowException {
        InvalidRowException invalidRowException = new InvalidRowException("Invalid row");
        List<String> messages = new ArrayList<>();
        if (customerEmail == null || customerEmail.isEmpty()) {
            messages.add("customer name is null or empty");
        }if (subjectOrName == null || subjectOrName.isEmpty()) {
            messages.add("subject or name is null or empty");
        }
        if (type == null || type.isEmpty()) {
            messages.add("type is null or empty");
        }
        if (!type.equals("ticket") && !type.equals("lead")) {
            messages.add("Type must be ticket or lead");
        }if (status == null || status.isEmpty()) {
            messages.add("status is null or empty");
        }if (!statusIn("meeting-to-schedule,scheduled,archived,success,assign-to-sales,open,assigned,on-hold,in-progress,resolved,closed,reopened,pending-customer-response,escalated")){
            messages.add("Status must be either meeting-to-schedule,scheduled,archived,success,assign-to-sales,open,assigned,on-hold,in-progress,resolved,closed,reopened,pending-customer-response,escalated");
        }if (expense == null) {
            messages.add("expense is null or empty");
        }if (expense < 0) {
            messages.add("expense is negative");
        }
        invalidRowException.setInvalidDesc(messages);
        if(!messages.isEmpty()) throw invalidRowException;
        return false;
    }
    private boolean statusIn(String pattern) {
        String[] patterns = pattern.split(",");
        boolean statuse = false;
        for (String s : patterns) {
            if (s.equals(status)) statuse = true;
        }
        return statuse;
    }
}
