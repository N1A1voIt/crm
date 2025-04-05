package site.easy.to.build.crm.dashboardController;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.service.customer.CustomerBudget;
import site.easy.to.build.crm.service.customer.CustomerBudgetDTO;
import site.easy.to.build.crm.service.lead.LeadsByEmploye;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class DashboardItem {
    List<CustomerBudgetDTO> customerBudget;
    LeadsByEmploye leadsByEmploye;
    Total total;


    @JsonProperty("totalBudget")
    double getTotalBudget(){
        double total = 0;
        for (CustomerBudgetDTO customerBudgetDTO : this.customerBudget) {
            total += customerBudgetDTO.getBudget().doubleValue();
        }
        return total;
    }
}
