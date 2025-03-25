package site.easy.to.build.crm.service.customer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerBudgetDTO {
    private BigDecimal budget;
    private CustomerDTO customer;

    // Getters and setters
}
