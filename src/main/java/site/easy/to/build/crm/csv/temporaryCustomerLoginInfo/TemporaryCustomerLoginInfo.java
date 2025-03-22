package site.easy.to.build.crm.csv.temporaryCustomerLoginInfo;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.csv.Validatable;

@Entity
@Table(name = "temp_table_clg")
@Getter
@Setter
public class TemporaryCustomerLoginInfo implements Validatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
//    @CsvBu
    @CsvBindByName(column = "username")
    @Column(name = "username")
    private String username;

    @CsvBindByName(column = "password")
    @Column(name = "password")
    private String password;

    @CsvBindByName(column = "token")
    @Column(name = "token")
    private String token;

    @CsvBindByName(column = "password_set")
    @Column(name = "password_set")
    private Boolean passwordSet;

    @Override
    public boolean isValid() {
        return true;
    }
}
