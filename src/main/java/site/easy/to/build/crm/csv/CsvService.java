package site.easy.to.build.crm.csv;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CsvService implements TemporaryCSVTableInterface {
    @PersistenceContext
    EntityManager em;
    @Override
    @Transactional
    public void createTemporaryTable() {
        em.createNativeQuery("CREATE TEMPORARY TABLE temp_table (id INT, name VARCHAR(255))")
                .executeUpdate();
    }

    @Override
    public List<Exception> controlCSV(List<String> lines,String entityName) {
        //Remove duplicate
//        Set<String> set = new HashSet<String>();
//        for (String line : lines) {
//            set.add(line);
//        }
//        set.stream().toList()
        List<Exception> exceptions = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            try {
                em.createNativeQuery("INSERT INTO temp_table (id, name) VALUES (1, 'John')")
                        .executeUpdate();
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        return exceptions;
    }

    @Override
    public void importCSV(List<String> lines, String entityName) {

    }

    @Transactional
    public void dropTemporaryTable() {
        em.createNativeQuery("DROP TABLE temp_table").executeUpdate();
    }
}
