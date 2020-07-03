package isd.alprserver.controller;

import isd.alprserver.dto.MailDTO;
import isd.alprserver.model.EmailResponse;
import isd.alprserver.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailController {
    private final MailService mailService;

    @PostMapping()
    public ResponseEntity<EmailResponse> sendEmailNotification(@RequestBody MailDTO mailDTO) {
        mailService.sendNotification(mailDTO.getMail());
        return ResponseEntity.ok(EmailResponse.builder().response("Email was sent!").build());
    }
}
