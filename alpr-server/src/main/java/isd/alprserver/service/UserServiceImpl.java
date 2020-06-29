package isd.alprserver.service;

import isd.alprserver.model.Company;
import isd.alprserver.model.Role;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import isd.alprserver.model.exceptions.UserCreationException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.repository.CompanyRepository;
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
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


    @Override
    @Transactional
    public boolean save(User user, String name) throws UserCreationException{
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserCreationException("The user with email "+user+" already exists.");
        }
        Company company = companyRepository.getByName(name).orElseThrow(() -> new CompanyNotFoundException("Company " + name + " doesn't exist" ));
        company.getUsers().add(user);
        user.setCompany(company);
        Role userRole = Role.builder().name("USER_ROLE").build();
        user.setRoles(Collections.singleton(roleRepository.save(userRole)));
        userRepository.save(user);
        return true;
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
    public void deleteById(int id) throws UserNotFoundException{
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

    @Override
    public User getById(int id) throws UserNotFoundException{
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("This user doesn't exist"));
    }
}
