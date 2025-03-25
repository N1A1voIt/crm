package site.easy.to.build.crm.csv.customerImp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import site.easy.to.build.crm.customValidations.customer.UniqueEmail;
import site.easy.to.build.crm.entity.Customer;

@Getter
@Setter
@Entity
@Table(name = "customer_imp")
public class CustomerImp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @UniqueEmail
    @Column(name = "customer_email", nullable = false, length = 250)
    private String customerEmail;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_name", nullable = false, length = 250)
    private String customerName;


}