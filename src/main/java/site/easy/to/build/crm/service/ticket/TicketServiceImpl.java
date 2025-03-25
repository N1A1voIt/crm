package site.easy.to.build.crm.service.ticket;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.depenses.entity.Expens;
import site.easy.to.build.crm.depenses.repository.ExpensesRepository;
import site.easy.to.build.crm.depenses.service.ExpensService;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.repository.TicketRepository;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.util.Frequency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    @Autowired
    private ExpensService expensService;
    @Autowired
    private ExpensesRepository expensesRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket findByTicketId(int id) {
        Ticket ticket = ticketRepository.findByTicketId(id);
//        Expens expens = expensService.findByTicketsId(id);
//        ticket.setDepense(expens.getAmount().doubleValue());
        return ticket;
    }

    @Override
    @Transactional
    public Ticket save(Ticket ticket) throws Exception {
//        Expens expens = new Expens();
//        expens.setAmount(BigDecimal.valueOf(ticket.getDepense()));
//        expens.setTickets(ticket);
        if(ticket.getDepense() < 0) throw new Exception("Valeure non valide");
        Ticket ticket1 = ticketRepository.save(ticket);
//        expens.setTicket(ticket1.getTicketId());
//        expens.setDaty(ticket1.getCreatedAt().toLocalDate());
//        expensService.save(expens);
        return ticket1;
    }

    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public List<Ticket> findManagerTickets(int id) {
        return ticketRepository.findByManagerId(id);
    }

    @Override
    public List<Ticket> findEmployeeTickets(int id) {
        return ticketRepository.findByEmployeeId(id);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findCustomerTickets(int id) {
        return ticketRepository.findByCustomerCustomerId(id);
    }

    @Override
    public List<Ticket> getRecentTickets(int managerId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ticketRepository.findByManagerIdOrderByCreatedAtDesc(managerId, pageable);
    }

    @Override
    public List<Ticket> getRecentEmployeeTickets(int employeeId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ticketRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId, pageable);
    }

    @Override
    public List<Ticket> getRecentCustomerTickets(int customerId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ticketRepository.findByCustomerCustomerIdOrderByCreatedAtDesc(customerId, pageable);
    }

    @Override
    public long countByEmployeeId(int employeeId) {
        return ticketRepository.countByEmployeeId(employeeId);
    }

    @Override
    public long countByManagerId(int managerId) {
        return ticketRepository.countByManagerId(managerId);
    }

    @Override
    public long countByCustomerCustomerId(int customerId) {
        return ticketRepository.countByCustomerCustomerId(customerId);
    }

    @Override
    public void deleteAllByCustomer(Customer customer) {
        ticketRepository.deleteAllByCustomer(customer);
    }

    @Override
    @Transactional
    public Map<LocalDate, BigDecimal> findCostPerTickets(String frequency) {
        List<Ticket> tickets = ticketRepository.findAll();
        Map<LocalDate, List<Ticket>> groupedTickets = new TreeMap<>();
        for (Ticket ticket : tickets) {
            if (ticket.getDepense() != null) {
                LocalDate adjustedDate = adjustDate(ticket.getCreatedAt().toLocalDate(), frequency);
                groupedTickets.putIfAbsent(adjustedDate, new ArrayList<>());
                groupedTickets.get(adjustedDate).add(ticket);
            }
        }

        Map<LocalDate, BigDecimal> costPerTicketMap = new LinkedHashMap<>();
        for (Map.Entry<LocalDate, List<Ticket>> entry : groupedTickets.entrySet()) {
            List<Ticket> ticketList = entry.getValue();
            BigDecimal totalCost = BigDecimal.ZERO;

            for (Ticket ticket : ticketList) {
                totalCost = totalCost.add(BigDecimal.valueOf(ticket.getDepense()));
            }

            BigDecimal averageCost = ticketList.size() > 0 ?
                    totalCost.divide(BigDecimal.valueOf(ticketList.size()), RoundingMode.HALF_UP) :
                    BigDecimal.ZERO;

            costPerTicketMap.put(entry.getKey(), averageCost);
        }

        return costPerTicketMap;
    }

    private LocalDate adjustDate(LocalDate date, String frequency) {
        switch (frequency) {
            case "DAILY":
                return date;
            case "WEEKLY":
                return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case "MONTHLY":
                return date.with(TemporalAdjusters.firstDayOfMonth());
            default:
                throw new IllegalArgumentException("Unsupported frequency: " + frequency);
        }
    }

}
