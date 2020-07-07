package isd.alprserver.controller;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.RoleNotFoundException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.exceptions.UserRemovalException;
import isd.alprserver.model.exceptions.UserUpdatingException;
import isd.alprserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO)
            throws RoleNotFoundException {

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userService.insert(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> updateUser(@RequestParam boolean isPasswordChanged,
                                        @RequestBody @Valid UserDTO userDTO)
            throws UserNotFoundException, RoleNotFoundException, UserUpdatingException {

        if (isPasswordChanged) {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userService.update(userDTO, isPasswordChanged);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> deleteUserById(@PathVariable int id)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> deleteUserByEmail(
            @RequestParam(name = "email") String email)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteByEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<User> findOneUser(@PathVariable int id)
            throws UserNotFoundException {

        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam String email,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword,
                                            @RequestParam(required = false)
                                                    String licensePlate)
            throws UserUpdatingException {

        userService.changePassword(email, oldPassword,
                newPassword, licensePlate, bCryptPasswordEncoder);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hascars")
    public ResponseEntity<Boolean> hasCars(@RequestParam String email) {

        return ResponseEntity.ok(userService.hasCars(email));
    }
}
