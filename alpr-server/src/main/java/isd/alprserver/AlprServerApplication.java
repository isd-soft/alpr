package isd.alprserver;

import isd.alprserver.model.Role;
import isd.alprserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlprServerApplication /*implements CommandLineRunner*/ {
    public static void main(String[] args) {
        SpringApplication.run(AlprServerApplication.class, args);
    }

//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Role userRole = Role.builder()
//                .name("USER")
//                .build();
//        Role adminRole = Role.builder()
//                .name("SYSTEM_ADMINISTRATOR")
//                .build();
//
//        roleRepository.save(userRole);
//        roleRepository.save(adminRole);
//    }
}
