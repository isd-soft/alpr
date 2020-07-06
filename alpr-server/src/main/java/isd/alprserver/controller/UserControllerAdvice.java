package isd.alprserver.controller;

import isd.alprserver.model.exceptions.UserCreationException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {UserController.class, RegistrationController.class})
public class UserControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class, UserCreationException.class})
    public ResponseEntity<Exception> handleUserNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
