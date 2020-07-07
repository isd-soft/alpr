package isd.alprserver.services.interfaces;

import isd.alprserver.dtos.UserDTO;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.RoleNotFoundException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.exceptions.UserRemovalException;
import isd.alprserver.model.exceptions.UserUpdatingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface UserService{
    void create(UserDTO userDTO) throws RoleNotFoundException;

    void deleteById(int id) throws UserNotFoundException, UserRemovalException;

    User getById(int Id) throws UserNotFoundException;

    User getUserByEmail(String email);

    List<UserDTO> getAll();

    void deleteByEmail(String email) throws UserNotFoundException, UserRemovalException;

    void update(UserDTO userDTO, boolean isPasswordChanged) throws UserNotFoundException, RoleNotFoundException, UserUpdatingException;

    void changePassword(String email, String oldPassword, String newPassword, String licensePlate, BCryptPasswordEncoder bCryptPasswordEncoder) throws UserUpdatingException;

    boolean hasCars(String email);
}
