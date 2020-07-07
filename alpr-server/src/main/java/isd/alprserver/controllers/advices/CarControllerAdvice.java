package isd.alprserver.controllers.advices;

import isd.alprserver.controllers.CarController;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CarController.class})
public class CarControllerAdvice {
    @ExceptionHandler({CarNotFoundException.class, CarAlreadyExistsException.class})
    public ResponseEntity<?> handleCarNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
