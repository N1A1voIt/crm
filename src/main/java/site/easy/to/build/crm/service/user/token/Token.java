package site.easy.to.build.crm.service.user.token;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @Column(name = "token", length = 250)
    private String token;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

}