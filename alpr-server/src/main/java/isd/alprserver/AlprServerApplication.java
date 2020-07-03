package isd.alprserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
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
