package ro.ubb.newsaggregator.service;

import org.springframework.web.multipart.MultipartFile;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;
import ro.ubb.newsaggregator.persistence.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(User user) throws UserException;

    void deleteUser(Long id) throws UserException;

    User updateUser(Long id, User user) throws UserException;

    List<User> getAllUsers();

    User getUserById(Long id) throws UserException;

    User getUserByEmail(String email) throws UserException;
}
