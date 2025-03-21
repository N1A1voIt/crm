package site.easy.to.build.crm.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenericCSVHandler<T, R> {

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
        return csvToBean.parse();
    }

    public void createTemporaryTable() {
        em.createNativeQuery(createTableQuery).executeUpdate();
    }

    public CsvTempResult<T> controlCSV(MultipartFile file) throws IOException {
        CsvTempResult<T> result = new CsvTempResult<>();
        List<T> temp = readCsv(file);
        List<Exception> exceptions = new ArrayList<>();
        for (T entity : temp) {
            try {
                em.persist(entity);
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        result.exceptions = exceptions;
        result.rows = temp;
        return result;
    }

    @Transactional
    public List<Exception> importCSV(MultipartFile file) throws IOException {
        try {
            createTemporaryTable();
            CsvTempResult<T> result = controlCSV(file);
            if (!result.exceptions.isEmpty()) {
                dropTemporaryTable();
                return result.exceptions;
            }
            Set<T> uniqueEntities = new HashSet<>(result.rows);
            for (T tempEntity : uniqueEntities) {
                try {
                    R entity = entityClass.getDeclaredConstructor().newInstance();
                    copyProperties(tempEntity, entity);
                    em.merge(entity);
                } catch (Exception e) {
                    throw new Exception(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dropTemporaryTable();
        }
        return null;
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

    public void dropTemporaryTable() {
        em.createNativeQuery("DROP TABLE " + tempTableName).executeUpdate();
    }

    public static class CsvTempResult<T> {
        public List<T> rows;
        public List<Exception> exceptions;
    }
}