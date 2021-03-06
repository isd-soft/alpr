package isd.alprserver.controllers.advices;

import isd.alprserver.controllers.StatisticsController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {StatisticsController.class})
@Slf4j
public class StatisticsControllerAdvice {
    @ExceptionHandler()
    public ResponseEntity<?> handleException(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
