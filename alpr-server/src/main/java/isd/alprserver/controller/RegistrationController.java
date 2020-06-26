package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.Response;
import isd.alprserver.service.CompanyService;
import isd.alprserver.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RegistrationController {
    private final CompanyService companyService;
    private final UserServiceImpl userServiceImpl;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> addUser(@RequestBody @Valid UserDTO user) {
        System.out.println(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(!userServiceImpl.save(user.toUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This email is already used");
        }

        return ResponseEntity.ok(new Response<>("Successfully Registered"));
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<Response<String>> sayHello() {
        return ResponseEntity.ok(new Response<>("Hello"));
    }
}
