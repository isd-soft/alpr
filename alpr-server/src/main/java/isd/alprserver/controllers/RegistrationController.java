package isd.alprserver.controllers;

import isd.alprserver.dtos.UserDTO;
import isd.alprserver.model.shared.Response;
import isd.alprserver.model.exceptions.RoleNotFoundException;
import isd.alprserver.services.interfaces.CompanyService;
import isd.alprserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Response<String>> addUser(@RequestBody @Valid UserDTO userDTO)
            throws RoleNotFoundException {

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole("USER");

        userService.create(userDTO);

        return ResponseEntity.ok(new Response<>("Successfully Registered"));
    }
}
