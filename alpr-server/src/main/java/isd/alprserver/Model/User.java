package isd.alprserver.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String firstName;

    private String lastName;

    private int age;

    private String telephoneNumber;

    private String password;

    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Car> cars;
}
