package site.easy.to.build.crm.depenses.entity;

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
@Table(name = "expenses")
public class Expens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Size(max = 250)
    @Column(name = "label", length = 250)
    private String label;

    @Column(name = "daty")
    private LocalDate daty;

    @Column(name = "ticket_id", nullable = false)
    private int ticket;

    @Column(name = "lead_id", nullable = false)
    private int lead;

}