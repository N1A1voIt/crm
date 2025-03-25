package site.easy.to.build.crm.csv.customerTemp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.GenericCSVHandler;
import site.easy.to.build.crm.csv.temporaryCustomerLoginInfo.TemporaryCustomerLoginInfo;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;

import java.io.IOException;
import java.util.List;
@Service
public class ImportCustomer {
    @Autowired
    GenericCSVHandler<CustomerTemp, CustomerForImp> customerLoginInfoGenericCSVHandler;

    public List<Exception> importCustomer(MultipartFile file) throws IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE `temp_customer` ( " +
                "   `customer_id` int unsigned NOT NULL AUTO_INCREMENT, " +
                "    `name` varchar(255) DEFAULT NULL, " +
                "    `phone` varchar(20) DEFAULT NULL, " +
                "    `address` varchar(255) DEFAULT NULL, " +
                "    `city` varchar(255) DEFAULT NULL, " +
                "    `state` varchar(255) DEFAULT NULL, " +
                "    `country` varchar(255) DEFAULT NULL, " +
                "    `user_id` int DEFAULT NULL, " +
                "    `description` text, " +
                "    `position` varchar(255) DEFAULT NULL, " +
                "    `twitter` varchar(255) DEFAULT NULL, " +
                "    `facebook` varchar(255) DEFAULT NULL, " +
                "    `youtube` varchar(255) DEFAULT NULL, " +
                "    `created_at` datetime DEFAULT NULL, " +
                "    `email` varchar(255) DEFAULT NULL, " +
                "    `profile_id` int DEFAULT NULL, " +
                "    PRIMARY KEY (`customer_id`), " +
                "    KEY `user_id` (`user_id`), " +
                "    KEY `profile_id` (`profile_id`) " +
                "  )";
        String tempTable = "temp_customer";
        customerLoginInfoGenericCSVHandler.setTempEntityClass(CustomerTemp.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(CustomerForImp.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        List<Exception> customer = customerLoginInfoGenericCSVHandler.importCSV(file);
//        customerLoginInfoGenericCSVHandler.safeDropTemporaryTable();
        return customer;
    }
}
