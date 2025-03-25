package site.easy.to.build.crm.csv.ticketLead;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.GenericCSVHandler;
import site.easy.to.build.crm.csv.customerTemp.CustomerForImp;
import site.easy.to.build.crm.csv.customerTemp.CustomerTemp;

import java.io.IOException;
import java.util.List;

@Service
public class ImportTicketLead {
    @Autowired
    GenericCSVHandler<TicketLeadImpTemp, TicketLeadImp> customerLoginInfoGenericCSVHandler;

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Exception> importTicketLead(MultipartFile file) throws IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_ticket_lead_imp (" +
                "    id INT PRIMARY KEY AUTO_INCREMENT," +
                "    customer_email VARCHAR(250) NOT NULL ," +
                "    subject_or_name VARCHAR(250)," +
                "    type VARCHAR(250) NOT NULL ," +
                "    status VARCHAR(250) check ( status='open' or" +
                "                                status='assigned' or" +
                "                                status='on-hold' or" +
                "                                status='in-progress' or" +
                "                                status='resolved' or" +
                "                                status='closed' or" +
                "                                status='reopened' or" +
                "                                status='pending-customer-response' or" +
                "                                status='escalated' or" +
                "                                status='archived' or" +
                "                                status='meeting-to-schedule' or" +
                "                                status='scheduled' or" +
                "                                status='archived' or" +
                "                                status='success' or" +
                "                                status='assign-to-sales'" +
                "                                )," +
                "    expense DECIMAL(18,2) NOT NULL CHECK ( expense > 0 )" +
                ")";
        String tempTable = "temp_ticket_lead_imp";
//        customerLoginInfoGenericCSVHandler.setEm(entityManager);
        customerLoginInfoGenericCSVHandler.setTempEntityClass(TicketLeadImpTemp.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(TicketLeadImp.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
//        customerLoginInfoGenericCSVHandler.dropTemporaryTable();
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        return customerLoginInfoGenericCSVHandler.importCSV(file);
    }
}
