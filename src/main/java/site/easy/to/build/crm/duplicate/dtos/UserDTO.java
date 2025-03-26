package site.easy.to.build.crm.duplicate.dtos;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.User;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private String status;
    private LocalDateTime createdAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }

}
