package site.easy.to.build.crm.controller;

import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.config.*;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.user.UserServiceImpl;
import site.easy.to.build.crm.util.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private  UserServiceImpl userService;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) throws Exception {
//        System.out.println("hbcjnk,zec");
//        System.out.println(passwordEncoder.encode(loginDto.getPassword()));
//        ApiResponse apiResponse = userService.findByUsernameAndPassword(loginDto.getUsername(),passwordEncoder.encode(loginDto.getPassword()));
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CrmUserDetails userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userDetails);
    }

    @GetMapping("/api/test/user")
    public String userAccess() {
        return "User Content.";
    }
}
