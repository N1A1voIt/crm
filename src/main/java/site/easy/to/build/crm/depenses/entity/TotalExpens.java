package site.easy.to.build.crm.depenses.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "total_expenses")
public class TotalExpens {
    @Column(name = "`lead_expense+tet.ticket_expense`", precision = 41, scale = 2)
    private BigDecimal leadExpenseTetTicketExpense;
    @Id
    @Column(name = "customer_id", columnDefinition = "int UNSIGNED not null")
    private Long customerId;

}