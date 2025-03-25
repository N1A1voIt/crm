package site.easy.to.build.crm.csv.budgetImp;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "budget_imp")
public class BudgetImp {
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
    @CsvNumber("#,##")
    @DecimalMin(value = "0.00", inclusive = false,message = "qesstxrcyvuybuierbveyuvujezvhzev")
    private BigDecimal budget;

}