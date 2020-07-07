package isd.alprserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableScheduling
public class AlprServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlprServerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(RoleRepository roleRepository,
//                                  UserService userService,
//                                  CompanyRepository companyRepository,
//                                  StatusRepository statusRepository,
//                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
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
//            UserDTO userDTO = UserDTO.builder()
//                    .age(22)
//                    .company("ISD")
//                    .email("admin@mail.com")
//                    .firstName("admin")
//                    .lastName("admin")
//                    .password("admin")
//                    .telephoneNumber("+37377777777")
//                    .role("SYSTEM_ADMINISTRATOR")
//                    .build();
//            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//
//            userService.insert(userDTO);
//
//            statusRepository.save(Status.builder()
//                    .name("OUT")
//                    .build());
//
//            statusRepository.save(Status.builder()
//                    .name("IN")
//                    .build());
//        };
//    }


}
