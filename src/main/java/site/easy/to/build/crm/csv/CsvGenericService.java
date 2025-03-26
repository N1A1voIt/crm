package site.easy.to.build.crm.csv;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.csv.budgetImp.BudgetImp;
import site.easy.to.build.crm.csv.budgetImp.BudgetImpService;
import site.easy.to.build.crm.csv.budgetImp.ImportBudgetImp;
import site.easy.to.build.crm.csv.customerImp.CustomerImp;
import site.easy.to.build.crm.csv.customerImp.CustomerImpService;
import site.easy.to.build.crm.csv.customerImp.CustomerImpTemp;
import site.easy.to.build.crm.csv.customerImp.ImportCustomerImp;
import site.easy.to.build.crm.csv.ticketLead.ImportTicketLead;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadArgs;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadImp;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadService;
import site.easy.to.build.crm.datageneration.DataGenerationService;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Role;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.repository.RoleRepository;
import site.easy.to.build.crm.repository.UserRepository;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.databaseCleanup.CleanUpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CsvGenericService {
    @Autowired
    ImportTicketLead importTicketLead;
    @Autowired
    ImportCustomerImp importCustomer;
    @Autowired
    DataGenerationService dataGenerationService;
    @Autowired
    CustomerImpService customerImpService;
    @Autowired
    CustomerService customerService;
    @Autowired
    TicketLeadService ticketLeadService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CleanUpService cleanUpService;
    @Autowired
    ImportBudgetImp importBudgetImp;
    @Autowired
    BudgetImpService budgetImpService;

    @PersistenceContext
    EntityManager em;


    public List<String> importCPL(MultipartFile file1, MultipartFile file2, MultipartFile file3, User user) throws CSVProcessingException {
//        em.getTransaction().begin();
        List<String> errors = new ArrayList<String>();
        try{
            try{
                importTicketLead.importTicketLead(file1);
            }catch (IOException e){
                CSVProcessingException exception = (CSVProcessingException) e.getCause();
                errors.addAll(exception.getErrors());
            }
            try {
                importCustomer.importCustomerImp(file2);
            }catch (IOException e) {
                CSVProcessingException exception = (CSVProcessingException) e.getCause();
                errors.addAll(exception.getErrors());
            }
            try {
                importBudgetImp.importBudget(file3);
            }catch (IOException e) {
                CSVProcessingException exception = (CSVProcessingException) e.getCause();
                errors.addAll(exception.getErrors());
            }
            if (errors.size() > 0) {
                CSVProcessingException exception = new CSVProcessingException("CSV errors", errors);
                throw exception;
            }

            saveEntities(user);
        }catch (Exception e){
//            em.getTransaction().rollback();
            throw e;
        }
        return errors;
    }
    public void saveEntities(User user) {
        String err = "";
        List<String> errors = new ArrayList<>();
        try{
            List<TicketLeadImp> ticketLeadImps = em.createNativeQuery("SELECT * FROM ticket_lead_imp",TicketLeadImp.class).getResultList();
            List<CustomerImp> customers = em.createNativeQuery("SELECT * FROM customer_imp",CustomerImp.class).getResultList();
            List<BudgetImp> budgetImps = em.createNativeQuery("SELECT * FROM budget_imp",BudgetImp.class).getResultList();
            for (int i = 0; i < customers.size(); i++) {
                Customer customer = customerImpService.toCustomer(customers.get(i), user);
                em.persist(customer);
            }
            TicketLeadArgs ticketLeadArgs = this.ticketLeadArgs(user);
            int l = 1;
            for (TicketLeadImp ticketLeadImp : ticketLeadImps) {
                try{
                    Object a = ticketLeadService.generateTicketOrLead(ticketLeadImp,ticketLeadArgs);
                    em.persist(a);
                }catch (Exception e){
                    errors.add("Error on line in ticketLead file:"+l+":"+e.getMessage()+"\n");
//                    throw new RuntimeException("Error on line in ticketLead file:"+l+":"+e.getMessage());
                }finally {
                    l++;
                }
            }
            l=1;
            for (BudgetImp budgetImp : budgetImps) {
               try{
                   Budget a = budgetImpService.getBudgets(budgetImp,ticketLeadArgs);
                    em.persist(a);
                }catch (Exception e){
                   errors.add("Error on line in budget file:"+l+":"+e.getMessage()+"\n");
//                   throw new RuntimeException("Error on line in Budget file:"+l+":"+e.getMessage());
                }finally {
                    l++;
                }
            }
            if (errors.size() > 0) {
                throw new CSVProcessingException("CSV errors", errors);
            }
        }catch (CSVProcessingException e){
            throw e;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
//            cleanUpService.cleanupImport();
            throw e;
        }
    }

    public TicketLeadArgs ticketLeadArgs(User user) throws CSVProcessingException {
        Role role = roleRepository.findByName("ROLE_EMPLOYEE");
        List<User> users = userRepository.findByRolesContains(role);
        TicketLeadArgs ticketLeadArgs = new TicketLeadArgs();
        ticketLeadArgs.setEmployee(users);
        HashMap<String, Customer> customerHashMap = new HashMap<>();
        List<Customer> customerList = customerService.findAll();
        for (Customer customer : customerList) {
            customerHashMap.put(customer.getEmail(), customer);
        }
        ticketLeadArgs.setCustomers(customerHashMap);
        ticketLeadArgs.setUser(user);
        return ticketLeadArgs;
    }
}
