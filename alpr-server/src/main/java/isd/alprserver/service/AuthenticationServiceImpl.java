package isd.alprserver.service;

import isd.alprserver.config.JwtTokenUtil;
import isd.alprserver.dto.UserDTO;
import isd.alprserver.model.JwtRequest;
import isd.alprserver.model.JwtResponse;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public JwtResponse authenticate(JwtRequest request) throws InvalidCredentialsException {
        String email = request.getEmail();
        UserDetails userDetails;
        try {
            userDetails = this.userService.loadUserByUsername(email);
        }
        catch (UsernameNotFoundException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String rawPassword = request.getPassword();
        String password = userDetails.getPassword();
        if (bCryptPasswordEncoder.matches(rawPassword, password)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenUtil.generateToken(userDetails);

            User user = userService.getUserByEmail(userDetails.getUsername());
            UserDTO userDTO = UserDTO.builder()
                    .role(user.getRole().getName())
                    .telephoneNumber(user.getTelephoneNumber())
                    .password("**********")
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .email(user.getEmail())
                    .company(user.getCompany().getName())
                    .age(user.getAge())
                    .build();

            return new JwtResponse(jwtToken, userDTO);
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }
}
