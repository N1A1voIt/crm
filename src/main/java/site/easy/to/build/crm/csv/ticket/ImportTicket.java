package site.easy.to.build.crm.csv.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.CSVProcessingException;
import site.easy.to.build.crm.csv.GenericCSVHandler;
import site.easy.to.build.crm.csv.customerTemp.CustomerForImp;
import site.easy.to.build.crm.csv.customerTemp.CustomerTemp;

import java.io.IOException;
import java.util.List;

@Service
public class ImportTicket {
    @Autowired
    GenericCSVHandler<TicketTemp, TicketForImp> customerLoginInfoGenericCSVHandler;

    public List<Exception> importCustomer(MultipartFile file) throws CSVProcessingException, IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE IF NOT EXISTS `temp_trigger_ticket` (" +
                "  `ticket_id` int unsigned NOT NULL AUTO_INCREMENT," +
                "  `subject` varchar(255) DEFAULT NULL," +
                "  `description` text," +
                "  `status` varchar(50) DEFAULT NULL," +
                "  `priority` varchar(50) DEFAULT NULL," +
                "  `customer_id` int unsigned NOT NULL," +
                "  `manager_id` int DEFAULT NULL," +
                "  `employee_id` int DEFAULT NULL," +
                "  `created_at` datetime DEFAULT NULL," +
                "  `depense` decimal(18,2) DEFAULT NULL," +
                "  PRIMARY KEY (`ticket_id`)," +
                "  KEY `fk_ticket_customer` (`customer_id`)," +
                "  KEY `fk_ticket_manager` (`manager_id`)," +
                "  KEY `fk_ticket_employee` (`employee_id`)" +
                ") ";
        String tempTable = "temp_trigger_ticket";
        customerLoginInfoGenericCSVHandler.setTempEntityClass(TicketTemp.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(TicketForImp.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        List<Exception> exceptions = customerLoginInfoGenericCSVHandler.importCSV(file);
//        System.out.println("gvhbjnzrk,bvzecfqxrdzacdtvybunzifdnuabvcrxdctvybfuni,zevzvbze");
        return exceptions;
    }
}
