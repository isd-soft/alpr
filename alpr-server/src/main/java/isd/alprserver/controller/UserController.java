package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.User;
import isd.alprserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user, "")); //todo fix
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO user) {
        User userById = userService.getUserByEmail(user.getEmail());
        userById.setFirstName(user.getFirstName());
        userById.setLastName(user.getLastName());
        userById.setAge(user.getAge());
        userById.setEmail(user.getEmail());
        userById.setPassword(user.getPassword());
        userService.update(userById);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteById(id);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user) {
        userService.delete(user);
    }

    @GetMapping("/{id}")
    public User findOneUser(@PathVariable int id) {
        return userService.getById(id);
    }
}
