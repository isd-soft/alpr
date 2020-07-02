package isd.alprserver.controller;


import isd.alprserver.model.JwtRequest;
import isd.alprserver.model.JwtResponse;
import isd.alprserver.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }
}
