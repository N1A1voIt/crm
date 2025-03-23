package site.easy.to.build.crm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_expens_lead")
public class VExpensLead {
    @Id
    @Column(name = "lead_id", columnDefinition = "int UNSIGNED not null")
    private Long leadId;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Size(max = 255)
    @Column(name = "customer_name")
    private String customerName;

}