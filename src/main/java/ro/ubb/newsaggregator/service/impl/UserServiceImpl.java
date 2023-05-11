package ro.ubb.newsaggregator.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;
import ro.ubb.newsaggregator.persistence.model.User;
import ro.ubb.newsaggregator.persistence.model.validator.UserValidator;
import ro.ubb.newsaggregator.persistence.repository.UserRepository;
import ro.ubb.newsaggregator.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    //TODO: SCHIMBATI PATHUL CU PATHUL ABSOLUT DE LA VOI DIN PROIECT SI SA SE TERMINE LA FINAL CU static\\images\\
    private final String FOLDER_PATH="D:\\faculta\\Anul 3\\pc\\CodeWarriorsServer\\petrescue\\src\\main\\resources\\static\\images\\";

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    public User saveUser(User user) throws UserException {
        userValidator.validateEmail(user.getEmail());
        userValidator.validatePhoneNumber(user.getPhoneNumber());

        if (userRepository.findUserByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new UserException("This phone number already belong to a registered user");
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserException("This email already belong to a registered user");
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("User with id="+id+" does not exist");
        }
        Optional<User> userFromRepository = userRepository.findById(id);
        userFromRepository.ifPresent(userRepository::delete);
    }

    public User updateUser(Long id, User user) throws UserException {

        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UserException("User with id="+id+" does not exist");
        }

        userValidator.validateEmail(user.getEmail());
        userValidator.validatePhoneNumber(user.getPhoneNumber());

        var userWithPhone=userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (userWithPhone.isPresent()&& userWithPhone.get().getId()!=id) {
            throw new UserException("This phone number already belong to a registered user");
        }

        user.setId(id);
        user.setPassword(optUser.get().getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserException{
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("User with id="+id+" does not exist");
        }

        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public User getUserByEmail(String email) throws UserException {
        if (userRepository.findUserByEmail(email).isEmpty()) {
            throw new UserException("User with this email does not exist");
        }
        return userRepository.findUserByEmail(email).get();
    }
}
