package site.easy.to.build.crm.depenses.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

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

}