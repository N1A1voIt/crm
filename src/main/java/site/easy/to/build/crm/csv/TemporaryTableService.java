package site.easy.to.build.crm.csv;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TemporaryTableService {

    private final EntityManager em;

    @Autowired
    public TemporaryTableService(EntityManager em) {
        this.em = em;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createTemporaryTable(String createTableQuery) {
        em.createNativeQuery(createTableQuery).executeUpdate();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void safeDropTemporaryTable(String tempTableName) {
        try {
            em.clear();
            em.createNativeQuery("DROP TABLE IF EXISTS " + tempTableName).executeUpdate();
            em.flush();
        } catch (Exception e) {
            System.err.println("Temp table drop failed: " + e.getMessage());
        }
    }
}

