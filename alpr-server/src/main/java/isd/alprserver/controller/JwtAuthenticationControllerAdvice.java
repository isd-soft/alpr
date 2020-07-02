package isd.alprserver.controller;

import isd.alprserver.model.Response;
import isd.alprserver.model.exceptions.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {JwtAuthenticationController.class})
public class JwtAuthenticationControllerAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response<String>> handleBadCredentials(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(e.getMessage()));
    }
}
