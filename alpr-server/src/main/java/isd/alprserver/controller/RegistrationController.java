package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.Response;
import isd.alprserver.service.CompanyService;
import isd.alprserver.service.UserService;
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
    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> addUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println(userDTO);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        if(!userService.save(userDTO.toUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>("This email is already used"));
        }

        return ResponseEntity.ok(new Response<>("Successfully Registered"));
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<Response<String>> sayHello() {
        return ResponseEntity.ok(new Response<>("Hello"));
    }
}
