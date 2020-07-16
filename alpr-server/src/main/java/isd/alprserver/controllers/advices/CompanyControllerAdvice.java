package isd.alprserver.controllers.advices;


import isd.alprserver.controllers.CompanyController;
import isd.alprserver.model.exceptions.CompanyCreationException;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CompanyController.class})
@Slf4j
public class CompanyControllerAdvice {
    @ExceptionHandler()
    public ResponseEntity<Exception> handleCompanyNotFound(Exception e) {
        log.warn("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
