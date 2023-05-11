package ro.ubb.newsaggregator.controller.advicer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ro.ubb.newsaggregator.persistence.model.CustomResponseEntity;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;

import javax.mail.MessagingException;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandleController {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleException(
            UserException userException, WebRequest request) {

        return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of(userException.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> handleMessageException(
            MessagingException messagingException, WebRequest request) {

        return new ResponseEntity<>(CustomResponseEntity.getErrors(List.of(messagingException.getMessage())), HttpStatus.EXPECTATION_FAILED);
    }
}
