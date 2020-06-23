package isd.alprserver.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Pattern(regexp = "([a-zA-Z0-9]+@[a-zA-Z0-9]/.[a-zA-Z])")
    private String email;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastName;

    @Min(18)
    private int age;

    @NotNull
    @Pattern(regexp = "/+373[0-9]{6}")
    private String telephoneNumber;

    @NotNull
    @Size(min = 2)
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Car> cars;

    @ManyToMany
    private Set<Role> roles;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
