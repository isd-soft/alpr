package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    @Transactional
    public boolean save(User user) throws UserCreationException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserCreationException("The user with email " + user + " already exists.");
        }
        if (user.getRole().getName().equals("USER_ROLE") ||
                user.getRole().getName().equals("SYSTEM_ADMINISTRATOR_ROLE")) {
            String companyName = user.getCompany().getName();
            Company company = companyRepository.getByName(companyName).orElseThrow(() ->
                    new CompanyNotFoundException("Company " + companyName + " doesn't exist"));
            Role role = roleRepository.save(user.getRole());
            userRepository.save(user);
//            role.getUsers().add(user);

            user.setRole(role);
            user.setCompany(company);
            company.getUsers().add(user);

            return true;
        }
        throw new UserCreationException("The user'r role " +
                user.getRole().getName() + " is not valid");
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
    public void deleteById(int id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

    @Override
    public User getById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(UserDTO.builder()
                    .age(user.getAge())
                    .company(user.getCompany().getName())
                    .role(user.getRole().getName())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .password("**********")
                    .telephoneNumber(user.getTelephoneNumber())
                    .build());
        }
        return userDTOS;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("This user doesn't exist"));
    }
}
