package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.dto.UserRequestDTO;
import isd.alprserver.model.User;

import java.util.List;

public interface UserService {
    boolean save(User user);
    User update(User user);
    void delete(User user);
    void deleteById(int id);
    User getById(int Id);
    List<User> getAll();
}
