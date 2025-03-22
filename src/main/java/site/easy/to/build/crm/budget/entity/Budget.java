package site.easy.to.build.crm.budget.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "budget")
    private BigDecimal budget;

    @Column(name = "date_min")
    private LocalDate dateMin;

    @Column(name = "date_max")
    private LocalDate dateMax;

    @Column(name = "designation")
    private String designation;

    @Column(name = "customer_id")
    private Integer customer;

}