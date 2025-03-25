package site.easy.to.build.crm.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import org.hibernate.exception.ConstraintViolationException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional(propagation = Propagation.MANDATORY,rollbackFor = {RuntimeException.class,CSVProcessingException.class})
public class GenericCSVHandler<T extends Validatable, R> {

    private final EntityManager em;
    private Class<T> tempEntityClass;
    private Class<R> entityClass;
    private String tempTableName;
    private String createTableQuery;

    public GenericCSVHandler(EntityManager em) {
        this.em = em;
    }

    public void setTempEntityClass(Class<T> tempEntityClass) {
        this.tempEntityClass = tempEntityClass;
    }

    public void setEntityClass(Class<R> entityClass) {
        this.entityClass = entityClass;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

    public void setCreateTableQuery(String createTableQuery) {
        this.createTableQuery = createTableQuery;
    }

    public List<T> readCsv(MultipartFile file) throws IOException{
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new InputStreamReader(file.getInputStream()))
                .withType(tempEntityClass)
                .build();
        return new ArrayList<>(new HashSet<>(csvToBean.parse()));
    }



//    public List<T> readCsv(MultipartFile file) throws IOException {
//        List<T> records = new ArrayList<>();
//        List<String> exceptions = new ArrayList<>();
//
//        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
//                    .withType(tempEntityClass)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .withThrowExceptions(false) // Prevent exceptions from being thrown
//                    .build();
//
//            records = csvToBean.parse();
//
//            // Capture exceptions with line numbers
//            for (CsvException e : csvToBean.getCapturedExceptions()) {
//                exceptions.add("Error on line " + e.getLineNumber() + ": " + e.getMessage());
//            }
//        } catch (Exception e) {
//            exceptions.add("Unexpected error: " + e.getMessage());
//        }
//
//        if (!exceptions.isEmpty()) {
//            throw new CSVProcessingException("Validation failed", exceptions);
//        }
//
//        return records;
//    }
//    @Transactional(propagation = Propagation.MANDATORY)
    public void createTemporaryTable() {
        em.createNativeQuery(createTableQuery).executeUpdate();
    }

//    @Transactional(propagation = Propagation.MANDATORY)
    public CsvTempResult<T> controlCSV(MultipartFile file) throws IOException {
        List<T> tempEntities = readCsv(file);
        List<String> exceptions = new ArrayList<>();

        for (int i = 0; i < tempEntities.size(); i++) {
            T entity = tempEntities.get(i);
            try {
                entity.isInvalid();
                if (entity.isValid()) {
                    em.persist(entity);
                }
            } catch (InvalidRowException e) {
                List<String> messages = e.getInvalidDesc();
                for (String message : messages) {
                    exceptions.add("Error on line " + (i + 1) + " in the file: " + file.getOriginalFilename() + ": " + message);
                }
            }
            catch (ConstraintViolationException e){
                e.printStackTrace();
                exceptions.add("Error on line " + (i + 1) + " in the file: " + file.getOriginalFilename() + ": " + e.getCause().getMessage());
                em.clear();
            }catch (Exception e) {
                e.printStackTrace();
                exceptions.add("Error on line " + (i + 1) + " in the file: " + file.getOriginalFilename() + ": " + e.getCause().getMessage());
                em.clear();
            }
        }

        if (!exceptions.isEmpty()) {
            throw new CSVProcessingException("Validation failed", exceptions);
        }
        return new CsvTempResult<>(tempEntities, Collections.emptyList());
    }

    public List<Exception> importCSV(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        createTemporaryTable();
        try {
            CsvTempResult<T> result = controlCSV(file);
            for (T tempEntity : result.rows) {
                R entity = createTargetEntity(tempEntity);
                em.merge(entity);
            }
            return Collections.emptyList();
        } catch (CSVProcessingException e) {
            throw new IOException("CSV processing failed", e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException("Unexpected error during CSV import (insertion on main tables)", e);
        }  catch (Exception e) {
            throw new RuntimeException("Unexpected error during CSV import (insertion on main tables)", e);
        } finally {
            dropTemporaryTable();
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
                // Handle or log the exception if necessary
            }
        }
    }

//    @Transactional(propagation = Propagation.MANDATORY)
    public void dropTemporaryTable() {
        try {
            em.clear();
            em.createNativeQuery("DROP TABLE IF EXISTS " + tempTableName).executeUpdate();
            em.flush();

            System.out.println("Temporary table " + tempTableName + " dropped successfully.");
        } catch (Exception e) {
            System.err.println("Failed to drop temporary table: " + e.getMessage());
        }
    }

}
