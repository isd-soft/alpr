package isd.alprserver.model.shared;

import isd.alprserver.dtos.UserDTO;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final UserDTO user;

    public JwtResponse(String jwtToken, UserDTO userDTO) {
        this.jwtToken = jwtToken;
        this.user = userDTO;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public UserDTO getUser() {
        return user;
    }
}
