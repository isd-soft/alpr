package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.User;
import isd.alprserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public @ResponseBody String addUser(@RequestBody @Valid UserDTO user) {
        System.out.println(user);
        if(!user.getPassword().equals(user.getPasswordConfirm())) {
            return "wrong pass";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(!userService.saveUser(user.convertToModel())) {
            return "already exist";
        }

        return "done";
    }

    @GetMapping(value = "/hello")
    public @ResponseBody String sayHello() {
        return "Hello";
    }
}
