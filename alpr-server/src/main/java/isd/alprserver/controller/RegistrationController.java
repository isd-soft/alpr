package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RegistrationController {
    private final UserServiceImp userServiceImp;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDTO user) {
        System.out.println(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(!userServiceImp.save(user.toUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This email is already used");
        }

        return ResponseEntity.ok("Account registered");
    }

    @GetMapping(value = "/hello")
    public @ResponseBody String sayHello() {
        return "Hello";
    }
}
