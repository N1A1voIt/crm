package site.easy.to.build.crm.depenses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.util.Frequency;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static site.easy.to.build.crm.util.Frequency.DAILY;
import static site.easy.to.build.crm.util.Frequency.WEEKLY;

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

    @Column(name = "ticket_id")
    private Integer ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id",insertable = false, updatable = false)
    private Ticket tickets;


    @Column(name = "lead_id")
    private Integer lead;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id",insertable = false, updatable = false)
    private Lead leads;





}