package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int nrParkingSpots;

    private byte[] logo;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "company",
            cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nrParkingSpots=" + nrParkingSpots +
                ", users=" + users.stream().map(User::getId).collect(Collectors.toList()) +
                '}';
    }

}
