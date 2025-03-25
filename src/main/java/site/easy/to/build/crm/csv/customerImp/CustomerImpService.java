package site.easy.to.build.crm.csv.customerImp;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.datageneration.DataGenerationService;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;

@Service
public class CustomerImpService {
    private Faker faker = new Faker();
    @Autowired
    DataGenerationService dataGenerationService;

    public Customer toCustomer(CustomerImp customerImp, User user) {
        Customer customer = new Customer();
        customer.setEmail(customerImp.getCustomerEmail());
        customer.setName(customerImp.getCustomerName());
        customer.setAddress(faker.address().streetAddress());
        customer.setCity(faker.address().city());
        customer.setCountry(faker.address().country());
        customer.setUser(user);
        customer.setDescription(faker.internet().slug());
        customer.setCreatedAt(dataGenerationService.generateRandomDate());
        customer.setPhone(faker.phoneNumber().phoneNumber());
        return customer;
    }

}
