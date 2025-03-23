package site.easy.to.build.crm.service.ticket;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CostPerTickets {
    LocalDate date;
    BigDecimal cost;
}
