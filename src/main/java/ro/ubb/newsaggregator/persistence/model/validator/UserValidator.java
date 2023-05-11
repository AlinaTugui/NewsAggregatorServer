package ro.ubb.newsaggregator.persistence.model.validator;

import org.springframework.stereotype.Component;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    public void validateEmail(String email) throws UserException {
        if(email==null){
            throw new UserException("Invalid email address");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new UserException("Invalid email address");
        }
    }

    public void validatePhoneNumber(String phoneNumber) throws UserException {
        if(phoneNumber==null){
            System.out.println("Null phone number");
            throw new UserException("Invalid phone number");
        }
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            System.out.println("phone number does not match");
            throw new UserException("Invalid phone number");
        }
    }

}
