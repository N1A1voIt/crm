package site.easy.to.build.crm.csv.ticketLead;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;

import java.util.HashMap;
import java.util.List;
@Getter
@Setter
public class TicketLeadArgs {
    String[] priorities = {"low", "medium", "high", "urgent", "critical"};
    HashMap<String,Customer> customers;
    List<User> employee;
    User user;
}
