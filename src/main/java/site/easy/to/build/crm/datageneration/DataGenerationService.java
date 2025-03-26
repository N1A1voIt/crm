package site.easy.to.build.crm.datageneration;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.repository.BudgetRepository;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.repository.*;
import site.easy.to.build.crm.service.user.UserServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DataGenerationService {
    private final Faker faker = new Faker();
    private final Random random = new Random();
    @Autowired
    CustomerLoginInfoRepository customerLoginInfoService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LeadRepository leadRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    BudgetRepository budgetRepository;

    public Customer getIdCustomer(List<Customer> customerList) {
        int index = random.nextInt(0,customerList.size());
        return customerList.get(index);
    }
    public List<User> getEmployee(Role role) {
        return userRepository.findByRolesContains(role);
    }

    public User getIdEmploye(List<User> userList) {
        int index = random.nextInt(0,userList.size());
        return userList.get(index);
    }

    public void generateData() {
        List<CustomerLoginInfo> customerLoginInfos = new ArrayList<>();
        int numbers = 100;
        for (int i = 0; i < numbers; i++) {
            CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
            customerLoginInfo.setEmail(faker.internet().emailAddress());
            customerLoginInfo.setPassword(faker.internet().password());
            customerLoginInfo.setUsername(faker.internet().emailAddress());
            customerLoginInfo.setToken(UUID.randomUUID().toString());
            customerLoginInfo.setPasswordSet(true);
            customerLoginInfos.add(customerLoginInfo);
        }
        customerLoginInfoService.saveAll(customerLoginInfos);
    }
    public void generateCustomer(User user) {
        List<Customer> customers = new ArrayList<>();
        int numbers = 100;
        for (int i = 0; i < numbers; i++) {
            Customer customer = new Customer();
            customer.setEmail(faker.internet().emailAddress());
            String phone = faker.phoneNumber().cellPhone();
            System.out.println("Phone:"+phone);
            customer.setPhone(phone);
            customer.setName(faker.name().fullName());
            customer.setAddress(faker.address().streetAddress());
            customer.setCity(faker.address().city());
            customer.setCountry(faker.address().country());
            customer.setUser(user);
            customer.setDescription(faker.internet().slug());
            customer.setCreatedAt(generateRandomDate());
            customers.add(customer);
        }
        customerRepository.saveAll(customers);
    }
    public LocalDateTime generateRandomDate() {
        long currentTime = System.currentTimeMillis();
        long yearInMillis = TimeUnit.DAYS.toMillis(365);
        long randomTime = currentTime - faker.random().nextLong(yearInMillis);
        Date randomDate = new Date(randomTime);
        return randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public LocalDateTime generateRandomDateLogically(LocalDateTime localDateTime) {
        Date referenceDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date randomDate = faker.date().future(365, TimeUnit.DAYS, referenceDate);
        return randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void generateLead(User user,List<Customer> customers) {
//        List<Customer> customers = customerRepository.findAll();
        Role role = roleRepository.findByName("ROLE_EMPLOYEE");
        List<User> users = userRepository.findByRolesContains(role);
        List<Lead> leads = new ArrayList<>();
        String[] status = "meeting-to-schedule,scheduled,archived,success,assign-to-sales".split(",");
        for (int i = 0; i < 100; i++) {
            Lead lead = new Lead();
            lead.setCustomer(getIdCustomer(customers));
            lead.setDepense(faker.number().randomDouble(2,500,5000));
            lead.setEmployee(users.get(random.nextInt(0,users.size())));
            lead.setCreatedAt(generateRandomDate());
            lead.setName(faker.leagueOfLegends().quote());
            String phone = faker.phoneNumber().phoneNumber();
            System.out.println("Phone:"+phone);
            lead.setPhone(phone);

            lead.setManager(user);
            lead.setCreatedAt(generateRandomDate());
            lead.setStatus(status[random.nextInt(0,status.length)]);
            leads.add(lead);
        }
        leadRepository.saveAll(leads);
    }
    public void generateTickets(User user,List<Customer> customers) {
//        List<Customer> customers = customerRepository.findAll();
        Role role = roleRepository.findByName("ROLE_EMPLOYEE");
        List<User> users = userRepository.findByRolesContains(role);
        List<Ticket> tickets = new ArrayList<>();

        String[] statuses = {
                "open", "assigned", "on-hold", "in-progress", "resolved",
                "closed", "reopened", "pending-customer-response", "escalated", "archived"
        };
        String[] priorities = {"low", "medium", "high", "urgent", "critical"};

        for (int i = 0; i < 100; i++) {
            Ticket ticket = new Ticket();
            ticket.setSubject(faker.lorem().sentence());
            ticket.setDescription(faker.lorem().paragraph());
            ticket.setStatus(statuses[random.nextInt(statuses.length)]);
            ticket.setPriority(priorities[random.nextInt(priorities.length)]);
            ticket.setCustomer(getIdCustomer(customers));
            ticket.setManager(user);
            ticket.setEmployee(users.get(random.nextInt(users.size())));
            ticket.setCreatedAt(generateRandomDate());
            ticket.setDepense(faker.number().randomDouble(2, 100, 5000));
            tickets.add(ticket);
        }
        ticketRepository.saveAll(tickets);
    }
    public void generateBudget(List<Customer> customers) {
        List<Budget> budgets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Budget budget = new Budget();
            double amount = faker.number().randomDouble(2, 100, 50000);
            budget.setBudget(BigDecimal.valueOf(amount));
            budget.setDesignation("Generation de :"+amount);
            budget.setCustomer(getIdCustomer(customers).getCustomerId());
            budgets.add(budget);
        }
        budgetRepository.saveAll(budgets);
    }
    @Transactional
    public void generateAll(User user) {
        generateCustomer(user);
        List<Customer> customers = customerRepository.findAll();
        generateLead(user, customers);
        generateTickets(user, customers);
        generateBudget(customers);
    }
}
