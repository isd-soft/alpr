package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.dto.UserRequestDTO;
import isd.alprserver.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    void deleteUserById(int id);
    User getUserById(int Id);
    List<User> getAllUsers();
}
