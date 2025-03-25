package site.easy.to.build.crm.csv.customerTemp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.Validatable;
import site.easy.to.build.crm.customValidations.customer.UniqueEmail;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "temp_customer")
public class CustomerTemp implements Validatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @CsvBindByName(column = "name")
    @Column(name = "name")
    @NotBlank(message = "Name is required", groups = {Default.class, Customer.CustomerUpdateValidationGroupInclusion.class})
    private String name;

    @CsvBindByName(column = "email")

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email format")
    @UniqueEmail
    private String email;
    @CsvBindByName(column = "position")

    @Column(name = "position")
    private String position;
    @CsvBindByName(column = "phone")

    @Column(name = "phone")
    private String phone;
    @CsvBindByName(column = "address")

    @Column(name = "address")
    private String address;

    @CsvBindByName(column = "city")

    @Column(name = "city")
    private String city;

    @CsvBindByName(column = "state")

    @Column(name = "state")
    private String state;

    @CsvBindByName(column = "country")
    @Column(name = "country")
    @NotBlank(message = "Country is required", groups = {Default.class, Customer.CustomerUpdateValidationGroupInclusion.class})
    private String country;
    @CsvBindByName(column = "description")

    @Column(name = "description")
    private String description;
    @CsvBindByName(column = "twitter")

    @Column(name = "twitter")
    private String twitter;

    @CsvBindByName(column = "facebook")

    @Column(name = "facebook")
    private String facebook;

    @CsvBindByName(column = "youtube")
    @Column(name = "youtube")
    private String youtube;

    @CsvBindByName(column = "user_id")
    @Column(name = "user_id")
    private Integer userId;

    @CsvBindByName(column = "profile_id")
    @Column(name = "profile_id")
    private Integer profileId;

    @CsvBindByName(column = "created_at")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CustomerTemp that = (CustomerTemp) o;
//        return Objects.equals(email, that.email) && Objects.equals(name, that.name);
//    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(email,name);
//    }

    @Override
    public boolean isValid() {
        return true;
    }
}
