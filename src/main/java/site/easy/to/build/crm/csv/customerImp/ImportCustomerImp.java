package site.easy.to.build.crm.csv.customerImp;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.CSVProcessingException;
import site.easy.to.build.crm.csv.GenericCSVHandler;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadImp;
import site.easy.to.build.crm.csv.ticketLead.TicketLeadImpTemp;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(propagation =  Propagation.REQUIRED,rollbackFor = {Exception.class, CSVProcessingException.class})
public class ImportCustomerImp {
    @Autowired
    GenericCSVHandler<CustomerImpTemp, CustomerImp> customerLoginInfoGenericCSVHandler;

    public List<Exception> importCustomerImp(MultipartFile file) throws IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_customer_imp (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    customer_email VARCHAR(250) NOT NULL UNIQUE," +
                "    customer_name VARCHAR(250) NOT NULL" +
                ")";
        String tempTable = "temp_customer_imp";
//        customerLoginInfoGenericCSVHandler.setEm(entityManager);
        customerLoginInfoGenericCSVHandler.setTempEntityClass(CustomerImpTemp.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(CustomerImp.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
        customerLoginInfoGenericCSVHandler.dropTemporaryTable();
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        List<Exception> customerImps = customerLoginInfoGenericCSVHandler.importCSV(file);
        customerLoginInfoGenericCSVHandler.dropTemporaryTable();
        return customerImps;
    }
}
