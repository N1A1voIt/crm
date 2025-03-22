package site.easy.to.build.crm.depenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easy.to.build.crm.depenses.entity.TotalExpens;

public interface TotalExpensRepository extends JpaRepository<TotalExpens,Integer> {
}
