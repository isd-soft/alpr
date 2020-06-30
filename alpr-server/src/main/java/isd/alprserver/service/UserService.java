package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.User;

import java.util.List;

public interface UserService {
    boolean save(User user);
    User update(User user);
    void delete(User user);
    void deleteById(int id);
    User getById(int Id);
    User getUserByEmail(String email);
    List<UserDTO> getAll();
}
