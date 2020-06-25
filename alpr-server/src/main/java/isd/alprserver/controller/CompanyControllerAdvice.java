package isd.alprserver.controller;

import isd.alprserver.model.exceptions.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CarController.class})
public class CompanyControllerAdvice {
    @ExceptionHandler (CarNotFoundException.class)
    public ResponseEntity<String> handleCompanyNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
