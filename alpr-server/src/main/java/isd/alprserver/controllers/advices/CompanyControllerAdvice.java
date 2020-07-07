package isd.alprserver.controllers.advices;


import isd.alprserver.controllers.CompanyController;
import isd.alprserver.model.exceptions.CompanyCreationException;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CompanyController.class})
public class CompanyControllerAdvice {
    @ExceptionHandler({CompanyNotFoundException.class,CompanyCreationException.class})
    public ResponseEntity<Exception> handleCompanyNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
