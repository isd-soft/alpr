package isd.alprserver.service;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.Role;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.*;
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
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void insert(UserDTO userDTO) throws UserCreationException, RoleNotFoundException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserCreationException("The user with email " + userDTO + " already exists.");
        }
        User user = userDTO.toUser();
        user.setCompany(getCompanyOfUser(userDTO));
        user.setRole(getRoleOfUser(userDTO));

        userRepository.save(user);
    }

    @Override
    public void deleteById(int id)
            throws UserNotFoundException, UserRemovalException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
        if (user.getRole().getName().equals("SYSTEM_ADMINISTRATOR_ROLE")) {
            throw new UserRemovalException("System administrator can not be deleted");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new
                UserNotFoundException("User with id " + id + " not found"));
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
    @Transactional
    public void deleteByEmail(String email)
            throws UserNotFoundException, UserRemovalException {
        User userByEmail = getUserByEmail(email);
        deleteById(userByEmail.getId());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, boolean isPasswordChanged)
            throws UserNotFoundException, RoleNotFoundException, UserUpdatingException {

        User storedUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " +
                        userDTO.getEmail() + " not found"));
        if (storedUser.getRole().getName().equals("SYSTEM_ADMINISTRATOR")) {
            throw new UserUpdatingException("System administrator can not be updated");
        }

        storedUser.setCompany(getCompanyOfUser(userDTO));
        storedUser.setRole(getRoleOfUser(userDTO));
        storedUser.setFirstName(userDTO.getFirstName());
        storedUser.setAge(userDTO.getAge());
        storedUser.setLastName(userDTO.getLastName());
        storedUser.setTelephoneNumber(userDTO.getTelephoneNumber());

        if (isPasswordChanged) {
            storedUser.setPassword(userDTO.getPassword());
        }

    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("This user doesn't exist"));
    }

    private Role getRoleOfUser(UserDTO userDTO) throws RoleNotFoundException {
        return roleRepository.findByName(userDTO.getRole()).orElseThrow(() ->
                new RoleNotFoundException("Role " + userDTO.getRole() + " does not exist"));
    }

    private Company getCompanyOfUser(UserDTO userDTO) {
        return companyRepository.getByName(userDTO.getCompany()).orElseThrow(() ->
                new CompanyNotFoundException("Company " + userDTO.getCompany() + " doesn't exist"));
    }
}
