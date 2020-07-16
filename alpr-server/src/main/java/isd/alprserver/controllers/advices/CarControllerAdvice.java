package isd.alprserver.controllers.advices;

import isd.alprserver.controllers.CarController;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CarController.class})
@Slf4j
public class CarControllerAdvice {
    @ExceptionHandler({CarNotFoundException.class, CarAlreadyExistsException.class})
    public ResponseEntity<?> handleCarNotFound(Exception e) {
        log.warn("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
