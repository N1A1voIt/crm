package site.easy.to.build.crm.depenses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "tota_expens_lead")
public class TotaExpensLead {
    @NotNull
    @Column(name = "lead_expense", nullable = false, precision = 40, scale = 2)
    private BigDecimal leadExpense;
    @Id
    @Column(name = "customer_id", columnDefinition = "int UNSIGNED not null")
    private Long customerId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

}