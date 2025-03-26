package site.easy.to.build.crm.duplicate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.budget.ObjectMappedBudget;
import site.easy.to.build.crm.budget.entity.Budget;
import site.easy.to.build.crm.budget.repository.BudgetRepository;
import site.easy.to.build.crm.budget.repository.ObjectMappedBudgetRepository;
import site.easy.to.build.crm.budget.service.BudgetService;
import site.easy.to.build.crm.csv.CsvGenericService;
import site.easy.to.build.crm.csv.budgetImp.BudgetImp;
import site.easy.to.build.crm.csv.budgetImp.BudgetImpService;
import site.easy.to.build.crm.csv.customerImp.CustomerImp;
import site.easy.to.build.crm.csv.customerImp.CustomerImpService;
import site.easy.to.build.crm.csv.customerImp.CustomerImpTemp;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadImp;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadService;
import site.easy.to.build.crm.duplicate.dtos.CustomerDTO;
import site.easy.to.build.crm.duplicate.dtos.TicketDTO;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuplicateService {
    @Autowired
    CustomerService customerService;
    @Autowired
    TicketService ticketService;
    @Autowired
    LeadService leadService;
    @Autowired
    BudgetService budgetService;
    @Autowired
    ObjectMappedBudgetRepository budgetRepository;

    public List<Ticket> getRelatedTicket(Customer customer) {
        return ticketService.findByCustomer(customer);
    }
    public List<Lead> getRelatedLead(Customer customer) {
        return leadService.getLeadByCustomerId(customer);
    }
    public List<ObjectMappedBudget> getRelatedBudget(Customer customer) {
        return budgetService.getBudgetsByCustomer(customer);
    }
    public void getFile(Customer customer) throws IOException {
        String budgetHeader = "customer_email,Budget";
        String CustomerHeader = "customer_email,customer_name";
        String ticketHeader = "customer_email,subject_or_name,type,status,expense";
        File file = new File("file-exp.csv");
        file.createNewFile();
//
//        if(file.createNewFile()) {
            FileWriter fw = new FileWriter(file);
//            fw.write(budgetHeader);
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.print("#Feuille customer");
            printWriter.print("\n"+CustomerHeader);
            printWriter.print("\n"+customer.toString());
            printWriter.print("\n"+"#Feuille ticket_lead");
            printWriter.print("\n"+ticketHeader);
            for (int i = 0; i < getRelatedTicket(customer).size(); i++) {
                System.out.println(getRelatedTicket(customer).get(i).toString());
                printWriter.print("\n"+getRelatedTicket(customer).get(i).toString());
            }
            for (int i = 0; i < getRelatedLead(customer).size(); i++) {
                printWriter.print("\n"+getRelatedLead(customer).get(i).toString());
            }
            printWriter.print("\n"+"#Feuille budget");
            printWriter.print("\n"+budgetHeader);
            for (int i = 0; i < getRelatedBudget(customer).size(); i++) {
                printWriter.print("\n"+getRelatedBudget(customer).get(i).toString());
            }
            printWriter.close();
            fw.close();
            //Test
//        }
    }

    public String exportJson(Customer customer) throws IOException {
        JsonDuplicate jsonDuplicate = new JsonDuplicate();
        List<Ticket> tickets = getRelatedTicket(customer);
        List<Lead> leads = getRelatedLead(customer);
        List<ObjectMappedBudget> budgets = getRelatedBudget(customer);
        jsonDuplicate.setCustomer(preprocessCustomer(customer));
        jsonDuplicate.setTickets(preprocessTickets(tickets,customer));
        jsonDuplicate.setLeads(preprocessLeads(leads,customer));
        jsonDuplicate.setBudgets(preprocessBudgets(budgets,customer));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = objectMapper.writeValueAsString(jsonDuplicate);

        File file = new File("file-exp.json");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(json);
        printWriter.close();
        fileWriter.close();
        return json;
    }
    private Customer preprocessCustomer(Customer customer) {
        customer.setCustomerId(null);
        customer.setEmail("copy_"+customer.getEmail());
        return customer;
    }
    private List<Ticket> preprocessTickets(List<Ticket> tickets,Customer customer) {
        for (Ticket ticket : tickets) {
            ticket.setTicketId(null);
            ticket.setCustomer(customer);
        }
        return tickets;
    }
    private List<Lead> preprocessLeads(List<Lead> leads,Customer customer) {
        for (Lead lead : leads) {
            lead.setLeadId(null);
            lead.setCustomer(customer);
        }
        return leads;
    }
    private List<ObjectMappedBudget> preprocessBudgets(List<ObjectMappedBudget> budgets, Customer customer) {
        for (ObjectMappedBudget budget : budgets) {
            budget.setId(null);
        }
        return budgets;
    }
//    public JsonDuplicate exportJson(Customer customer) throws IOException {
//        JsonDuplicate jsonDuplicate = new JsonDuplicate();
//        List<Ticket> tickets = getRelatedTicket(customer);
//        List<Lead> leads = getRelatedLead(customer);
//        List<Budget> budgets = getRelatedBudget(customer);
//        jsonDuplicate.setCustomer(new CustomerDTO(preprocessCustomer(customer)));
//        jsonDuplicate.setTickets(preprocessTickets(tickets,customer).stream().map(TicketDTO::new).collect(Collectors.toList()));
//        jsonDuplicate.setLeads(preprocessLeads(leads,customer));
//        jsonDuplicate.setBudgets(preprocessBudgets(budgets,customer));
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String json = objectMapper.writeValueAsString(jsonDuplicate);
//
//        File file = new File("file-exp.json");
//        FileWriter fileWriter = new FileWriter(file);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//        printWriter.print(json);
//        printWriter.close();
//        fileWriter.close();
//        return jsonDuplicate;
//    }
    @Transactional
    public void importJSON(MultipartFile jsonFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonDuplicate jsonDuplicate = objectMapper.readValue(jsonFile.getInputStream(), JsonDuplicate.class);
        Customer customer = customerService.save(jsonDuplicate.getCustomer());
        for (int i = 0; i < jsonDuplicate.getTickets().size(); i++) {
            jsonDuplicate.getTickets().get(i).setCustomer(customer);
            ticketService.save(jsonDuplicate.getTickets().get(i));
        }for (int i = 0; i < jsonDuplicate.getLeads().size(); i++) {
            jsonDuplicate.getLeads().get(i).setCustomer(customer);
            leadService.save(jsonDuplicate.getLeads().get(i));
        }for (int i = 0; i < jsonDuplicate.getBudgets().size(); i++) {
            jsonDuplicate.getBudgets().get(i).setCustomer(jsonDuplicate.getCustomer());
            budgetRepository.save(jsonDuplicate.getBudgets().get(i));
        }
    }

//    public void splitCsv(MultipartFile csvFile, User user) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
//            String line;
//            int lineNumber = 0;
//
//            while ((line = reader.readLine()) != null) {
//                System.out.println("L"+line);
//                String inf = "customer";
//                if (line.contains("#Feuille")) {
//                    if (line.contains("customer")) {
//                       inf = "customer";
//                    }
//                    if (line.contains("ticket_lead")) {
//                       inf = "ticket_lead";
//                    }
//                    if (line.contains("budget")) {
//                       inf = "budget";
//                    }
//                    System.out.println(reader.readLine());
//                    System.out.println(reader.readLine());
//                }
//                System.out.println(line);
//                if (inf.contains("customer")) {
//                    System.out.println("Saving customer");
//                    StringReader stringReader = new StringReader(line);
//                    CsvToBean<CustomerImp> csvToBean = new CsvToBeanBuilder<CustomerImp>(stringReader)
//                            .withType(CustomerImp.class)
//                            .withSeparator(',')
//                            .withIgnoreLeadingWhiteSpace(true)
//                            .build();
//                    List<CustomerImp> records = csvToBean.parse();
//
//                    customerService.save(customerImpService.toCustomer(records.get(0),user));
//                }
//                if (inf.contains("ticket_lead")) {
//                    StringReader stringReader = new StringReader(line);
//                    CsvToBean<TicketLeadImp> csvToBean = new CsvToBeanBuilder<TicketLeadImp>(stringReader)
//                            .withType(TicketLeadImp.class)
//                            .withSeparator(',')
//                            .withIgnoreLeadingWhiteSpace(true)
//                            .build();
//                    List<TicketLeadImp> records = csvToBean.parse();
//                    Object a = ticketLeadService.generateTicketOrLead(records.get(0), csvGenericService.ticketLeadArgs(user));
//                    if (a instanceof Ticket) {
//                        Ticket ticket = (Ticket) a;
//                        ticketService.save(ticket);
//                    }
//                    if (a instanceof Lead) {
//                        Lead ticket = (Lead) a;
//                        leadService.save(ticket);
//                    }
//                }
//                if (inf.contains("budget")) {
//                    StringReader stringReader = new StringReader(line);
//                    CsvToBean<BudgetImp> csvToBean = new CsvToBeanBuilder<BudgetImp>(stringReader)
//                            .withType(BudgetImp.class)
//                            .withSeparator(',')
//                            .withIgnoreLeadingWhiteSpace(true)
//                            .build();
//                    List<BudgetImp> records = csvToBean.parse();
//                    Budget a = budgetImpService.getBudgets(records.get(0), csvGenericService.ticketLeadArgs(user));
//                    budgetRepository.save(a);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
