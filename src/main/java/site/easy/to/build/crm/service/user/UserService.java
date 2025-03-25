package site.easy.to.build.crm.service.user;

import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.util.ApiResponse;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public long countAllUsers();

    public User findById(int id);

    public List<User> findByUsername(String username);

    public User findByEmail(String email);

    public User findByToken(String token);

    public User save(User user);

    public void deleteById(int id);

    public List<User> findAll();

    public ApiResponse findByUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException;


}
