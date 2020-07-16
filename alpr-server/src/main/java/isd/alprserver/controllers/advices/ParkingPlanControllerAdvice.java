package isd.alprserver.controllers.advices;

import isd.alprserver.controllers.CarController;
import isd.alprserver.controllers.ParkingPlanController;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import isd.alprserver.model.exceptions.ParkingPlanNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ParkingPlanController.class})
@Slf4j
public class ParkingPlanControllerAdvice {
    @ExceptionHandler({ParkingPlanNotFoundException.class})
    public ResponseEntity<?> handleParkingPlanNotFound(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
