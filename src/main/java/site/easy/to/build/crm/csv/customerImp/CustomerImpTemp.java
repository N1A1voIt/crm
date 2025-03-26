package site.easy.to.build.crm.csv.customerImp;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.InvalidRowException;
import site.easy.to.build.crm.csv.Validatable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @CsvBindByPosition(position = 0)
    private String customerEmail;

    @Size(max = 250)
    @NotNull
    @Column(name = "customer_name", nullable = false, length = 250)
    @CsvBindByName(column = "customer_name")
    @CsvBindByPosition(position = 1)
    private String customerName;

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isInvalid() throws InvalidRowException {
        InvalidRowException invalidRowException = new InvalidRowException("Invalid row");
        List<String> messages = new ArrayList<>();
        if (customerEmail == null || customerEmail.isEmpty()) {
            messages.add("customer name is null or empty");
        }if (customerEmail == null || customerEmail.isEmpty()) {
            messages.add("customer name is null or empty");
        }
        invalidRowException.setInvalidDesc(messages);
        if(!messages.isEmpty()) throw invalidRowException;

        return false;
    }
}