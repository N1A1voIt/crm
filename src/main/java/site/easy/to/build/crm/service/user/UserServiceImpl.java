package site.easy.to.build.crm.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.UserRepository;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.token.Token;
import site.easy.to.build.crm.service.user.token.TokenRepository;
import site.easy.to.build.crm.util.ApiResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    private String generateToken(String username) throws NoSuchAlgorithmException {
        long currentTime = System.currentTimeMillis();  // Get current timestamp

        // Combine username and timestamp
        String data = username + "-" + currentTime;

        // Optional: Hash the data for added security
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

        // Encode the result to Base64 for token format
        return Base64.getEncoder().encodeToString(hashBytes);
    }
    @Override
    public ApiResponse findByUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException {
        User user = userRepository.findByUsernameAndPassword(username,password).orElse(null);
        List<Exception> exceptions = new ArrayList<>();
        if (user == null) {
            exceptions.add(new RuntimeException("user not found"));
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData("");
            apiResponse.setStatus(400);
            apiResponse.setExceptions(exceptions);
            return apiResponse;
        }
        if (!user.getRoles().contains("ROLE_MANAGER")){
            exceptions.add(new RuntimeException("user does not have ROLE_MANAGER"));
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData("");
            apiResponse.setStatus(400);
            apiResponse.setExceptions(exceptions);
            return apiResponse;
        }
        String token = generateToken(username);
        Token tokene = new Token();
        tokene.setToken(token);
        tokene.setIdUser(user.getId());
        tokenRepository.save(tokene);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(tokene);
        apiResponse.setStatus(200);
        apiResponse.setExceptions(null);
        return apiResponse;
    }
}
