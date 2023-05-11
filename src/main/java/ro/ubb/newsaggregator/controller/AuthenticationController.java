package ro.ubb.newsaggregator.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.ubb.newsaggregator.dto.UserInfoRequest;
import ro.ubb.newsaggregator.dto.UserInfoResponse;
import ro.ubb.newsaggregator.persistence.model.CustomResponseEntity;
import ro.ubb.newsaggregator.persistence.model.User;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;
import ro.ubb.newsaggregator.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    //private final EmailService emailService;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        //this.emailService=emailService;
    }

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody Map<String, String> userCredentials){
        String email = userCredentials.get("email");
        String password = userCredentials.get("password");
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (UserException e) {
            return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of("Invalid email!")),
                    HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of("Invalid password!")),
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(getUserDTOWithToken(user), HttpStatus.OK);
    }

    @GetMapping("/logout/{email}")
    ResponseEntity<Object> logout(@PathVariable String email){
        return new ResponseEntity<>(CustomResponseEntity.getMessage("Logged out successfully!"), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    ResponseEntity<Object> refreshToken(@RequestBody Map<String, String> userCredentials){
        String email = userCredentials.get("email");
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (UserException e) {
            return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of("Invalid email!")),
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(getUserDTOWithToken(user), HttpStatus.OK);
    }

    @PostMapping("/signup")
    ResponseEntity<Object> signup(@RequestBody UserInfoRequest userCredentials){
        User user = modelMapper.map(userCredentials, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.saveUser(user);
        } catch (UserException e) {
            return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of(e.getMessage())),
                    HttpStatus.BAD_REQUEST);
        }
        /*try {
            emailService.sendEmail(user.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
        return new ResponseEntity<>(CustomResponseEntity.getMessage("Signup successful"), HttpStatus.OK);
    }

    private UserInfoResponse getUserDTOWithToken(User user) {
        String token = getJWTToken(user.getId());
        UserInfoResponse userInfo = modelMapper.map(user, UserInfoResponse.class);
        userInfo.setToken(token);
        return userInfo;
    }

    private String getJWTToken(Long id) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(id.toString())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (864000 * 1000)))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }

}

