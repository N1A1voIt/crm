package site.easy.to.build.crm.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class GenericCSVHandler<T extends Validatable, R> {

    private final EntityManager em;
    private Class<T> tempEntityClass;
    private Class<R> entityClass;
    private  String tempTableName;
    private  String createTableQuery;

    public Class<T> getTempEntityClass() {
        return tempEntityClass;
    }

    public void setTempEntityClass(Class<T> tempEntityClass) {
        this.tempEntityClass = tempEntityClass;
    }

    public Class<R> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<R> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTempTableName() {
        return tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

    public String getCreateTableQuery() {
        return createTableQuery;
    }

    public void setCreateTableQuery(String createTableQuery) {
        this.createTableQuery = createTableQuery;
    }

    public GenericCSVHandler(EntityManager em) {
        this.em = em;
    }

    public List<T> readCsv(MultipartFile file) throws IOException {
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(
                new InputStreamReader(file.getInputStream()))
                .withType(tempEntityClass)
                .build();
        Set<T> uniqueEntities = new HashSet<>(csvToBean.parse());

        return new ArrayList<>(uniqueEntities);
    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createTemporaryTable() {
        em.createNativeQuery(createTableQuery).executeUpdate();
    }
//    @Transactional(propagation = Propagation.MANDATORY)
//    public CsvTempResult<T> controlCSV(MultipartFile file) throws IOException {
//        List<T> temp = readCsv(file);
//        List<String> exceptions = new ArrayList<>();
//        int count = 1;
//        for (T entity : temp) {
//            try {
//                if (entity.isValid()) {
//                    em.persist(entity);
//                }
//            } catch (Exception e) {
//                exceptions.add("Error on line "+count+" in the file:"+file.getName()+":"+e.getCause().getMessage());
//                em.clear();
//            }
//            count++;
//        }
//
//        if (!exceptions.isEmpty()) {
//            for (String e : exceptions) {
//                System.out.println(e);
//            }
//            throw new CSVProcessingException("CSV validation failed", exceptions);
//        }
//
//        return new CsvTempResult<T>(temp, new ArrayList<>());
//    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CsvTempResult<T> controlCSV(MultipartFile file) throws IOException {
        List<T> temp = readCsv(file);
        List<String> exceptions = new ArrayList<>();

        for(int i = 0; i < temp.size(); i++) {
            T entity = temp.get(i);
            try {
                if (entity.isValid()) {
                    em.persist(entity);
                    if(i % 20 == 0) {
                        em.flush();
                        em.clear();
                    }
                }
            } catch (Exception e) {
//                exceptions.add("Line %d error: %s".formatted(i+1, getRootCause(e)));
                exceptions.add("Error on line "+(i+1)+" in the file:"+file.getName()+":"+e.getCause().getMessage());
//                em.getTransaction().setRollbackOnly();
//                break; // Stop processing on critical errors
            }
        }

        if (!exceptions.isEmpty()) {
            throw new CSVProcessingException("Validation failed", exceptions);
        }
        return new CsvTempResult<>(temp, Collections.emptyList());
    }

    //    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void safeDropTemporaryTable() {
        try {
            em.clear(); // Detach all entities before DDL operation
            em.createNativeQuery("DROP TABLE IF EXISTS " + tempTableName).executeUpdate();
            em.flush(); // Ensure operation completes immediately
        } catch (Exception e) {
            System.err.println("Temp table drop failed: " + e.getMessage());
            // Consider adding deadlock retry logic here
        }
    }


    //    @Transactional(rollbackFor = Exception.class)
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Exception> importCSV(MultipartFile file) throws IOException {
        try {
            createTemporaryTable();
            CsvTempResult<T> result = controlCSV(file);

            for (T tempEntity : result.rows) {
                R entity = createTargetEntity(tempEntity);
                em.merge(entity);
            }

            return Collections.emptyList();
        } catch (CSVProcessingException e) {
            throw new IOException("CSV processing failed", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            safeDropTemporaryTable();
        }
    }

    private R createTargetEntity(T source) throws Exception {
        R target = entityClass.getDeclaredConstructor().newInstance();
        copyProperties(source, target);
        return target;
    }

    private void copyProperties(T source, R target) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Field targetField = target.getClass().getDeclaredField(field.getName());
                targetField.setAccessible(true);
                targetField.set(target, field.get(source));
            } catch (NoSuchFieldException e) {
            }
        }
    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.MANDATORY)
    public void dropTemporaryTable() {
        em.createNativeQuery("DROP TABLE IF EXISTS " + tempTableName).executeUpdate();
    }
}