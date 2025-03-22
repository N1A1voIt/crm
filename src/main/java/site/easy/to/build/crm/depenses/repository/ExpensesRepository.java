package site.easy.to.build.crm.depenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easy.to.build.crm.depenses.entity.Expens;

import java.util.Optional;

public interface ExpensesRepository extends JpaRepository<Expens,Integer> {
    Optional<Expens> findByTicketsCustomerCustomerId(Integer tickets_customer_customerId);
    Optional<Expens> findByLeadsCustomerCustomerId(Integer leads_customer_customerId);
    Optional<Expens> findByTicket(Integer ticket);
    Optional<Expens> findByLead(Integer lead);
}
