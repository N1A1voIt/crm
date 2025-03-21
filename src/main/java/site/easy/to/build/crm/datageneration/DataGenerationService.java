package site.easy.to.build.crm.datageneration;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.repository.CustomerLoginInfoRepository;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class DataGenerationService {
    private final Faker faker = new Faker();
    private final Random random = new Random();
    @Autowired
    CustomerLoginInfoRepository customerLoginInfoService;
    public void generateData(){
        List<CustomerLoginInfo> customerLoginInfos = new ArrayList<>();
        int numbers = 100;
        for (int i = 0; i < numbers; i++) {
            CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
            customerLoginInfo.setEmail(faker.internet().emailAddress());
            customerLoginInfo.setPassword(faker.internet().password());
            customerLoginInfo.setUsername(faker.internet().emailAddress());
            customerLoginInfo.setToken(UUID.randomUUID().toString());
            customerLoginInfo.setPasswordSet(true);
            customerLoginInfos.add(customerLoginInfo);
        }
        customerLoginInfoService.saveAll(customerLoginInfos);
    }
}
