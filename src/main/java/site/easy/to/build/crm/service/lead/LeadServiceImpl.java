package site.easy.to.build.crm.service.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.depenses.entity.Expens;
import site.easy.to.build.crm.depenses.service.ExpensService;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.repository.LeadRepository;
import site.easy.to.build.crm.entity.Lead;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    @Autowired
    private ExpensService expensService;

    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public Lead findByLeadId(int id) {
        Lead lead = leadRepository.findByLeadId(id);
//        Expens expens = expensService.findByLeadsId(id);
//        lead.setDepense(expens.getAmount().doubleValue());
        return lead;
    }

    @Override
    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    @Override
    public List<Lead> findAssignedLeads(int userId) {
        return leadRepository.findByEmployeeId(userId);
    }

    @Override
    public List<Lead> findCreatedLeads(int userId) {
        return leadRepository.findByManagerId(userId);
    }

    @Override
    public Lead findByMeetingId(String meetingId){
        return leadRepository.findByMeetingId(meetingId);
    }
    @Override
    public Lead save(Lead lead) {
//        Expens expens = new Expens();
//        expens.setAmount(BigDecimal.valueOf(lead.getDepense()));
        Lead lead1 = leadRepository.save(lead);
//        expens.setLeads(lead1);
//        expens.setLead(lead1.getLeadId());
//        expens.setDaty(lead.getCreatedAt().toLocalDate());
//        expensService.save(expens);
        return lead1;
    }

    @Override
    public void delete(Lead lead) {
        leadRepository.delete(lead);
    }

    @Override
    public List<Lead> getRecentLeadsByEmployee(int employeeId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return leadRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId, pageable);
    }

    @Override
    public List<Lead> getRecentCustomerLeads(int customerId, int limit) {
        Pageable pageable = PageRequest.of(0,limit);
        return leadRepository.findByCustomerCustomerIdOrderByCreatedAtDesc(customerId, pageable);
    }

    @Override
    public void deleteAllByCustomer(Customer customer) {
        leadRepository.deleteAllByCustomer(customer);
    }

    @Override
    public LeadsByEmploye leadsByEmployee() {
        Map<Integer,Integer> count = new HashMap<>();
        Map<Integer,User> users = new HashMap<>();
        List<Lead> leads = leadRepository.findAll();
        for (Lead lead : leads) {
            lead.getEmployee().setPassword(null);
            users.computeIfAbsent(lead.getEmployee().getId(), k -> lead.getEmployee());
            count.put(lead.getEmployee().getId(), count.getOrDefault(lead.getEmployee().getId(), 0) + 1);
        }
        List<LeadEmp> leadsByEmployes = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : count.entrySet()) {
            LeadEmp leadEmp = new LeadEmp();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.get(entry.getKey()).getId());
            userDTO.setName(users.get(entry.getKey()).getUsername());
            userDTO.setEmail(users.get(entry.getKey()).getEmail());
            leadEmp.setEmployee(userDTO);
            leadEmp.setLeads(entry.getValue());
            leadsByEmployes.add(leadEmp);
        }
        LeadsByEmploye leadsByEmploye = new LeadsByEmploye();
        leadsByEmploye.setLeadEmpList(leadsByEmployes);
        return leadsByEmploye;
    }

    @Override
    public List<Lead> getLeadByCustomerId(Customer customer) {
        return leadRepository.findByCustomerCustomerId(customer.getCustomerId());
    }

    @Override
    public List<Lead> getRecentLeads(int managerId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return leadRepository.findByManagerIdOrderByCreatedAtDesc(managerId, pageable);
    }

    @Override
    public List<Lead> getCustomerLeads(int customerId) {
        return leadRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public long countByEmployeeId(int employeeId) {
        return leadRepository.countByEmployeeId(employeeId);
    }

    @Override
    public long countByManagerId(int managerId) {
        return leadRepository.countByManagerId(managerId);
    }

    @Override
    public long countByCustomerId(int customerId) {
        return leadRepository.countByCustomerCustomerId(customerId);
    }


}
