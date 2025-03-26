package site.easy.to.build.crm.csv.budgetImp;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.InvalidRowException;
import site.easy.to.build.crm.csv.Validatable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "temp_budget_imp")
public class BudgetImpTemp implements Validatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_email", nullable = false, length = 250)
    @CsvBindByName(column = "customer_email")
    @CsvBindByPosition(position = 0)
    private String customerEmail;

    @NotNull
    @Column(name = "budget", nullable = false, precision = 18, scale = 2)
    @CsvBindByName(column = "Budget")
    @DecimalMin(value = "0.00", inclusive = false,message = "qesstxrcyvuybuierbveyuvujezvhzev")
    @CsvBindByPosition(position = 1)
    @CsvNumber("#,##")
    private BigDecimal budget;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isInvalid() throws InvalidRowException {
        InvalidRowException invalidRowException = new InvalidRowException("Invalid row");
        List<String> messages = new ArrayList<>();
        if (customerEmail == null || customerEmail.isEmpty()) {
            messages.add("customerEmail is null or empty");
        }if (budget == null || budget.compareTo(BigDecimal.ZERO) < 0) {
            messages.add("budget is null or negative");
        }
        invalidRowException.setInvalidDesc(messages);
        if(!messages.isEmpty()) throw invalidRowException;
        return false;
    }
}