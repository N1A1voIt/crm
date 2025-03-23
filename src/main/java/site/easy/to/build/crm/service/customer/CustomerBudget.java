package site.easy.to.build.crm.service.customer;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;
@Getter
@Setter
public class CustomerBudget {
    Customer customer;
    BigDecimal budget;
}

