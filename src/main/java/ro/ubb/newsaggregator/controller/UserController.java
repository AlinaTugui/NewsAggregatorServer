package ro.ubb.newsaggregator.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.newsaggregator.dto.UserInfoRequest;
import ro.ubb.newsaggregator.dto.UserInfoResponse;
import ro.ubb.newsaggregator.persistence.model.CustomResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.ubb.newsaggregator.persistence.model.User;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;
import ro.ubb.newsaggregator.service.EmailService;
import ro.ubb.newsaggregator.service.UserService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userServiceImpl;

    private final ModelMapper modelMapper;

    private final EmailService emailService;

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable Long id) throws UserException {
        User user = userServiceImpl.getUserById(id);
        UserInfoResponse userInfoResponse = modelMapper.map(user, UserInfoResponse.class);

        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<UserInfoResponse> userInfoResponseList = userServiceImpl.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserInfoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(userInfoResponseList, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserInfoResponse> getUserByEmail(@RequestParam("email") String email) throws UserException {
        User user = userServiceImpl.getUserByEmail(email);
        UserInfoResponse userInfoResponse = modelMapper.map(user, UserInfoResponse.class);

        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserInfoResponse> updateUser(@RequestBody UserInfoRequest userInfoRequest) throws UserException {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = Long.parseLong(userId);
        User user = modelMapper.map(userInfoRequest, User.class);
        user.setId(id);
        User updatedUser = userServiceImpl.updateUser(id, user);
        return new ResponseEntity<>(modelMapper.map(updatedUser, UserInfoResponse.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserInfoResponse> saveUser(@RequestBody UserInfoRequest userInfoRequest) throws UserException, MessagingException {
        User user = modelMapper.map(userInfoRequest, User.class);
        User savedUser = userServiceImpl.saveUser(user);
        emailService.sendEmail(user.getEmail());
        return new ResponseEntity<>(modelMapper.map(savedUser, UserInfoResponse.class), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUser() throws UserException {
        Long userId = (Long)SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        userServiceImpl.deleteUser(userId);
        return new ResponseEntity<>(CustomResponseEntity.getMessage("User deleted successfully"), HttpStatus.OK);
    }

}
