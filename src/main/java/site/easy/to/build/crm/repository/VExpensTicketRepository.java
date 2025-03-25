package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easy.to.build.crm.entity.VExpensTicket;

public interface VExpensTicketRepository extends JpaRepository<VExpensTicket, Integer> {
}
