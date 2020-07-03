package isd.alprserver.dto;

import isd.alprserver.model.Company;
import isd.alprserver.model.Role;
import isd.alprserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    @NotNull
    @Pattern(regexp = "([a-zA-Z0-9_.+-]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+)")
    private String email;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastName;

    @NotNull
    @Min(18)
    private int age;

    @NotNull
    @Pattern(regexp = "\\+373[0-9]{8}")
    private String telephoneNumber;

    @NotNull
    @Size(min = 2)
    private String password;

    private String company;

    private String role;

    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .age(age)
                .telephoneNumber(telephoneNumber)
                .firstName(firstName)
                .role(role != null ? Role.builder().name(role).build()
                        : Role.builder().name("USER").build())
                .lastName(lastName)
                .company(Company.builder().name(company).build())
                .build();
    }
}
