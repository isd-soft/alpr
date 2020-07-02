package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private long id;

    private String name;

    private int nrParkingSpots;

    @Transient
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "company",
            //orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

}
