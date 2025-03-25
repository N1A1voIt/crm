package site.easy.to.build.crm.csv.budgetImp;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.Validatable;

import java.math.BigDecimal;

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
    private String customerEmail;

    @NotNull
    @Column(name = "budget", nullable = false, precision = 18, scale = 2)
    @CsvBindByName(column = "Budget")
    private BigDecimal budget;

    @Override
    public boolean isValid() {
        return false;
    }
}