package isd.alprserver.services;

import isd.alprserver.dtos.UserDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.Company;
import isd.alprserver.model.Role;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.*;
import isd.alprserver.model.statistics.UserAudit;
import isd.alprserver.repositories.CarRepository;
import isd.alprserver.repositories.CompanyRepository;
import isd.alprserver.repositories.RoleRepository;
import isd.alprserver.repositories.UserRepository;
import isd.alprserver.services.interfaces.StatisticsService;
import isd.alprserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final CarRepository carRepository;
    private final StatisticsService statisticsService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void create(UserDTO userDTO) throws UserCreationException, RoleNotFoundException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserCreationException("This email is already used");
        }
        User user = userDTO.toUser();
        user.setCompany(getCompanyOfUser(userDTO));
        user.setRole(getRoleOfUser(userDTO));
        user.setPhoto(userDTO.getPhoto() != null ?
                Base64.getDecoder().decode(userDTO.getPhoto().split(",")[1]) :
                null);

        user = userRepository.save(user);

        Date registrationDate = new Date();

        UserAudit userAudit = UserAudit.builder()
                .email(user.getEmail())
                .telephoneNumber(user.getTelephoneNumber())
                .registrationDate(registrationDate)
                .build();

        statisticsService.auditUserRegistration(userAudit);
    }

    @Override
    public void deleteById(int id)
            throws UserNotFoundException, UserRemovalException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
        if (user.getRole().getName().equals("SYSTEM_ADMINISTRATOR")) {
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
            throws RoleNotFoundException, UserUpdatingException {

        User storedUser = getUserByDTO(userDTO);
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
    public void changePassword(String email, String oldPassword,
                               String newPassword, String licensePlate,
                               BCryptPasswordEncoder bCryptPasswordEncoder)
            throws UserUpdatingException {

        User user = getUserByEmail(email);

        if (user.getRole().getName().equals("SYSTEM_ADMINISTRATOR")) {
            changeOldPasswordToNew(oldPassword, newPassword, user,
                    bCryptPasswordEncoder);
        } else if (hasCars(email)) {
            Optional<Car> car = carRepository.findByLicensePlate(licensePlate);
            if (!car.isPresent()) {
                throw new UserUpdatingException(
                        "Car with license plate " + licensePlate + " not found");
            } else {
                if (car.get().getUser() == user) {
                    changeOldPasswordToNew(oldPassword, newPassword, user,
                            bCryptPasswordEncoder);
                } else {
                    throw new UserUpdatingException("Car with license plate " +
                            licensePlate + " does not belong to user " + email);
                }
            }
        } else {
            changeOldPasswordToNew(oldPassword, newPassword, user,
                    bCryptPasswordEncoder);
        }
    }

    private void changeOldPasswordToNew(String oldPassword, String newPassword, User user,
                                        BCryptPasswordEncoder bCryptPasswordEncoder)
            throws UserUpdatingException {

        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        } else {
            throw new UserUpdatingException("Incorrect old password");
        }
    }

    @Override
    @Transactional
    public boolean hasCars(String email) {

        return (getUserByEmail(email).getCars().size() > 0);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with email " +
                        email + " not found"));
    }

    private User getUserByDTO(UserDTO userDTO) {
        return getUserByEmail(userDTO.getEmail());
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
