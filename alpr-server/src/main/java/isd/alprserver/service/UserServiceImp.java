package isd.alprserver.service;

import isd.alprserver.model.Role;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.repository.RoleRepository;
import isd.alprserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Override
    public boolean save(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return false;
        }
        Role userRole = Role.builder().name("USER_ROLE").build();
        user.setRoles(Collections.singleton(roleRepository.save(userRole)));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(int userId) {
        if(userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User with id " + user.getId() + " not found"));
        userRepository.delete(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
