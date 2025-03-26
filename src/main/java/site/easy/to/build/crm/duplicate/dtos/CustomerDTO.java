package site.easy.to.build.crm.duplicate.dtos;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;

import java.time.LocalDateTime;
@Getter
@Setter
public class CustomerDTO {

    private Integer customerId;
    private String name;
    private String email;
    private String position;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String description;
    private String twitter;
    private String facebook;
    private String youtube;
    private LocalDateTime createdAt;

    private UserDTO user;

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.position = customer.getPosition();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
        this.city = customer.getCity();
        this.state = customer.getState();
        this.country = customer.getCountry();
        this.description = customer.getDescription();
        this.twitter = customer.getTwitter();
        this.facebook = customer.getFacebook();
        this.youtube = customer.getYoutube();
        this.createdAt = customer.getCreatedAt();

        if (customer.getUser() != null) {
            this.user = new UserDTO(customer.getUser());
        }
    }

    // Getters and setters
    // ...
}
