package isd.alprserver.controllers.advices;

import isd.alprserver.controllers.RegistrationController;
import isd.alprserver.controllers.UserController;
import isd.alprserver.model.exceptions.UserCreationException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.exceptions.UserUpdatingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {UserController.class, RegistrationController.class})
@Slf4j
public class UserControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class,
            UserCreationException.class,
            UserUpdatingException.class})
    public ResponseEntity<Exception> handleUserException(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
