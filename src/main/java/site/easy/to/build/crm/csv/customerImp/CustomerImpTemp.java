package site.easy.to.build.crm.csv.customerImp;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.Validatable;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "temp_customer_imp")
public class CustomerImpTemp implements Validatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_email", nullable = false, length = 250)
    @CsvBindByName(column = "customer_email")
    private String customerEmail;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_name", nullable = false, length = 250)
    @CsvBindByName(column = "customer_name")
    private String customerName;

    @Override
    public boolean isValid() {
        return true;
    }
}