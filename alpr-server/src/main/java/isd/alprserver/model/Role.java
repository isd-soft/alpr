package isd.alprserver.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "role",
            cascade = CascadeType.ALL)
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
