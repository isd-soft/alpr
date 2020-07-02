package isd.alprserver;

import isd.alprserver.model.Role;
import isd.alprserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
//    public CommandLineRunner initRoles(RoleRepository roleRepository){
//        return args -> {
//                   Role userRole = Role.builder()
//                .name("USER")
//                .build();
//        Role adminRole = Role.builder()
//                .name("SYSTEM_ADMINISTRATOR")
//                .build();
//        roleRepository.save(userRole);
//        roleRepository.save(adminRole);
//        };
//    }
}
