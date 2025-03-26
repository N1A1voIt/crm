package site.easy.to.build.crm.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "budgets")
public class ObjectMappedBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "budget")
    @Positive(message = "Budget must be positive")
    private BigDecimal budget;

    @Column(name = "date_min")
    private LocalDate dateMin;

    @Column(name = "date_max")
    private LocalDate dateMax;

    @Column(name = "designation")
    private String designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    Customer customer;

}
