package isd.alprserver;

import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.Role;
import isd.alprserver.repository.CompanyRepository;
import isd.alprserver.repository.RoleRepository;
import isd.alprserver.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlprServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlprServerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner initRoles(RoleRepository roleRepository,
//                                       UserService userService,
//                                       CompanyRepository companyRepository) {
//
//        return args -> {
//            Role userRole = Role.builder()
//                    .name("USER")
//                    .build();
//            Role adminRole = Role.builder()
//                    .name("SYSTEM_ADMINISTRATOR")
//                    .build();
//            roleRepository.save(userRole);
//            roleRepository.save(adminRole);
//
//            companyRepository.save(Company.builder()
//                    .name("ISD")
//                    .nrParkingSpots(22)
//                    .build());
//
//            userService.insert(UserDTO.builder()
//                    .age(22)
//                    .company("ISD")
//                    .email("admin@mail.com")
//                    .firstName("admin")
//                    .lastName("admin")
//                    .password("admin")
//                    .telephoneNumber("+37377777777")
//                    .role("SYSTEM_ADMINISTRATOR")
//                    .build());
//        };
//    }
}
