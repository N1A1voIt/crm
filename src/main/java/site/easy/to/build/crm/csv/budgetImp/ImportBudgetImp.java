package site.easy.to.build.crm.csv.budgetImp;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.GenericCSVHandler;

import java.io.IOException;
import java.util.List;

@Service
public class ImportBudgetImp {
    @Autowired
    GenericCSVHandler<BudgetImpTemp, BudgetImp> customerLoginInfoGenericCSVHandler;

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Exception> importBudget(MultipartFile file) throws IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_budget_imp(" +
                "       id INT PRIMARY KEY AUTO_INCREMENT," +
                "       customer_email VARCHAR(250) NOT NULL ," +
                "       budget DECIMAL(18,2) NOT NULL CHECK (budget > 0)" +
                ")";
        String tempTable = "temp_budget_imp";
//        customerLoginInfoGenericCSVHandler.setEm(entityManager);
        customerLoginInfoGenericCSVHandler.setTempEntityClass(BudgetImpTemp.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(BudgetImp.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
//        customerLoginInfoGenericCSVHandler.dropTemporaryTable();
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        return customerLoginInfoGenericCSVHandler.importCSV(file);
    }
}
