package isd.alprserver.dto;
import isd.alprserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRequestDTO {
    private long id;
    private String email;

    public User toUser() {
        return User.builder()
                .id(id)
                .email(email).build();
    }
}
