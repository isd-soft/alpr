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
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO)
            throws RoleNotFoundException {

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userService.insert(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/update")
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
    public ResponseEntity<?> deleteUserById(@PathVariable int id)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByEmail(
            @RequestParam(name = "email") String email)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOneUser(@PathVariable int id)
            throws UserNotFoundException {

        return ResponseEntity.ok(userService.getById(id));

    }

//    @GetMapping
//    public ResponseEntity<?> findUserByEmail(
//            @RequestParam(name = "email") String email)
//            throws UserNotFoundException {
//        userService.getUserByEmail(email);
//        return new ResponseEntity<>(HttpStatus.OK);
//
//    }
}
