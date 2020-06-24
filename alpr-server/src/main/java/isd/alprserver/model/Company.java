package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int nrParkingSpots;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> users;
}
