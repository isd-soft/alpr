package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.dto.UserRequestDTO;
import isd.alprserver.model.User;

import java.util.List;

public interface UserServiceInt {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    void deleteUserById(long id);
    User getUserById(long Id);
    List<User> getAllUsers();
}
