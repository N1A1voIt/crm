package site.easy.to.build.crm.budget.entity;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name = "total_budget_customer")
@Immutable
public class BudgetCustomer {
    @Id
    @Column(name = "customer_id")
    Integer customerId;
    @Column(name = "budget")
    BigDecimal budget;
    @Column(name = "name")
    String name;
}
