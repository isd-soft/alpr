package isd.alprserver.controllers;

import isd.alprserver.dtos.LicensePlateDTO;
import isd.alprserver.dtos.MailDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.shared.EmailResponse;
import isd.alprserver.model.exceptions.CarNotFoundException;
import isd.alprserver.services.interfaces.CarService;
import isd.alprserver.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailController {
    private final MailService mailService;
    private final CarService carService;

    @PostMapping()
    public ResponseEntity<EmailResponse> sendEmailNotification(@RequestBody MailDTO mailDTO) {
        mailService.sendNotification(mailDTO.getMail());
        return ResponseEntity.ok(EmailResponse.builder().response("Email was sent!").build());
    }

    @PostMapping("/license-plate")
    public ResponseEntity<EmailResponse> sendEmailByLicensePlate(@RequestBody LicensePlateDTO plate) {
        Car car = carService.getByLicensePlate(plate.getLicensePlate()).orElseThrow(() -> new CarNotFoundException("Unknown license plate " + plate.getLicensePlate()));
        mailService.sendNotification(car.getUser().getEmail());
        return ResponseEntity.ok(EmailResponse.builder().response("Email was sent!").build());
    }
}
