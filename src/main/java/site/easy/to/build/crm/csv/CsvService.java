package site.easy.to.build.crm.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.temporaryCustomerLoginInfo.TemporaryCLGRepository;
import site.easy.to.build.crm.csv.temporaryCustomerLoginInfo.TemporaryCustomerLoginInfo;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.repository.CustomerLoginInfoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CsvService {
    @PersistenceContext
    EntityManager em;
    @Autowired
    TemporaryCLGRepository temporaryCLGRepository;
    @Autowired
    CustomerLoginInfoRepository customerLoginInfoRepository;

    public List<TemporaryCustomerLoginInfo> readCsv(MultipartFile file) throws IOException {
        CsvToBean<TemporaryCustomerLoginInfo> csvToBean = new CsvToBeanBuilder<TemporaryCustomerLoginInfo>(
                new InputStreamReader(file.getInputStream()))
                .withType(TemporaryCustomerLoginInfo.class)
                .build();
        return csvToBean.parse();
    }

    public void createTemporaryTable() {
        em.createNativeQuery("CREATE TEMPORARY TABLE temp_table_clg (" +
                        "`id` int NOT NULL AUTO_INCREMENT," +
                        "    `password` varchar(255) DEFAULT NULL," +
                        "    `username` varchar(255) DEFAULT NULL," +
                        "    `token` varchar(500) DEFAULT NULL," +
                        "    `password_set` tinyint(1) DEFAULT '0'," +
                        "    PRIMARY KEY (`id`)," +
                        "    UNIQUE KEY `token` (`token`)" +
                        "  )")
                .executeUpdate();
    }


    public CsvTempResult controlCSV(MultipartFile file) throws IOException {
        //Remove duplicate
//        Set<String> set = new HashSet<String>();
//        for (String line : lines) {
//            set.add(line);
//        }
//        set.stream().toList()
        CsvTempResult result = new CsvTempResult();
        List<TemporaryCustomerLoginInfo> temp = readCsv(file);
        List<Exception> exceptions = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            try{
                temporaryCLGRepository.save(temp.get(i));
            } catch (Exception e){
                exceptions.add(e);
            }
        }
        result.exceptions = exceptions;
        result.rows = temp;
        return result;
    }
   @Transactional
    public List<Exception> importCSV(MultipartFile file) throws IOException {
        try{
            createTemporaryTable();
            CsvTempResult result = controlCSV(file);
            if (!result.exceptions.isEmpty()) {
                dropTemporaryTable();
                return result.exceptions;
            }
            List<TemporaryCustomerLoginInfo> entities = (List<TemporaryCustomerLoginInfo>) result.rows;
            for (TemporaryCustomerLoginInfo entity : entities) {
                CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
                customerLoginInfo.setEmail(entity.getUsername());
                customerLoginInfo.setPassword(entity.getPassword());
                customerLoginInfo.setUsername(entity.getUsername());
                customerLoginInfo.setToken(entity.getToken());
                customerLoginInfo.setPasswordSet(entity.getPasswordSet());
                customerLoginInfoRepository.save(customerLoginInfo);
            }
        } finally {
            dropTemporaryTable();
        }
        return null;
    }

    public void dropTemporaryTable() {
        em.createNativeQuery("DROP TABLE temp_table_clg").executeUpdate();
    }
}
