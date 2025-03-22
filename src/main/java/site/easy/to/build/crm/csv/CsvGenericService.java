package site.easy.to.build.crm.csv;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.temporaryCustomerLoginInfo.TemporaryCustomerLoginInfo;
import site.easy.to.build.crm.entity.CustomerLoginInfo;

import java.io.IOException;
import java.util.List;

@Service
public class CsvGenericService {
    @Autowired
    GenericCSVHandler<TemporaryCustomerLoginInfo, CustomerLoginInfo> customerLoginInfoGenericCSVHandler;

    public List<Exception> importCLG(MultipartFile file) throws IOException {
        String tempTableScript = "CREATE TEMPORARY TABLE temp_table_clg (" +
                "`id` int NOT NULL AUTO_INCREMENT," +
                "    `password` varchar(255) DEFAULT NULL," +
                "    `username` varchar(255) DEFAULT NULL," +
                "    `token` varchar(500) DEFAULT NULL," +
                "    `password_set` tinyint(1) DEFAULT '0'," +
                "    PRIMARY KEY (`id`)," +
                "    UNIQUE KEY `token` (`token`)" +
                "  )";
        String tempTable = "temp_table_clg";
        customerLoginInfoGenericCSVHandler.setTempEntityClass(TemporaryCustomerLoginInfo.class);
        customerLoginInfoGenericCSVHandler.setEntityClass(CustomerLoginInfo.class);
        customerLoginInfoGenericCSVHandler.setTempTableName(tempTable);
        customerLoginInfoGenericCSVHandler.setCreateTableQuery(tempTableScript);
        return customerLoginInfoGenericCSVHandler.importCSV(file);
    }
}
