package site.easy.to.build.crm.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.*;

public class MultipleCSVReader {
    private final Map<String, Class<?>> tableEntityMapping = new HashMap<>();
    private final Map<Class<?>, List<?>> results = new HashMap<>();
    private final Map<Class<?>, List<CsvException>> errors = new HashMap<>();

    public void registerTable(String tableIdentifier, Class<?> entityClass) {
        tableEntityMapping.put(tableIdentifier.toLowerCase(), entityClass);
    }

    public void processCsv(Reader csvReader) throws IOException {
        try (BufferedReader reader = new BufferedReader(csvReader)) {
            String line;
            Class<?> currentEntityClass = null;
//            HeaderColumnNameMappingStrategy<?> currentStrategy = null;
//            CsvToBean<?> currentCsvToBean = null;
            List<String> currentSectionLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("TABLE:")) {
                    if (currentEntityClass != null) {
                        processSection(currentEntityClass, currentSectionLines);
                        currentSectionLines.clear();
                    }

                    String tableIdentifier = line.split(":")[1].trim().toLowerCase();
                    currentEntityClass = tableEntityMapping.get(tableIdentifier);
//                    if (currentEntityClass != null) {
//                        currentStrategy = new HeaderColumnNameMappingStrategy<>();
//                        currentStrategy.setType(currentEntityClass);
//                        currentCsvToBean = new CsvToBeanBuilder<>(new StringReader(""))
//                                .withMappingStrategy(currentStrategy)
//                                .withThrowExceptions(false)
//                                .build();
//                    }
                } else if (currentEntityClass != null) {
                    currentSectionLines.add(line);
                }
            }

            if (currentEntityClass != null && !currentSectionLines.isEmpty()) {
                processSection(currentEntityClass, currentSectionLines);
            }
        }
    }

    private <T> void processSection(Class<T> entityClass, List<String> lines) {
        try (StringReader sectionReader = new StringReader(String.join("\n", lines))) {
            MappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(entityClass);

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(sectionReader)
                    .withMappingStrategy(strategy)
                    .withThrowExceptions(false)
                    .build();

            List<T> entities = new ArrayList<>();
            List<CsvException> sectionErrors = new ArrayList<>();

            for (Iterator<T> it = csvToBean.iterator(); it.hasNext();) {
                try {
                    T entity = it.next();
                    entities.add(entity);
                } catch (RuntimeException e) {
                    if (e.getCause() instanceof CsvException) {
                        sectionErrors.add((CsvException) e.getCause());
                    }
                }
            }

            results.put(entityClass, entities);
            errors.put(entityClass, sectionErrors);
        }
    }
    public static void main(String[] args) throws Exception {
        MultipleCSVReader processor = new MultipleCSVReader();

//        processor.registerTable("users", User.class);
//        processor.registerTable("orders", Order.class);
//        processor.registerTable("products", Product.class);

        try (Reader reader = new FileReader("data.csv")) {
            processor.processCsv(reader);
        }
//
//        List<User> users = processor.getEntities(User.class);
//        List<Order> orders = processor.getEntities(Order.class);
//        List<Product> products = processor.getEntities(Product.class);
    }

}
