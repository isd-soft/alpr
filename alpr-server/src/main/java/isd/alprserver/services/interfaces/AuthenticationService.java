package isd.alprserver.services.interfaces;

import isd.alprserver.model.shared.JwtRequest;
import isd.alprserver.model.shared.JwtResponse;
import isd.alprserver.model.exceptions.InvalidCredentialsException;

public interface AuthenticationService{
    JwtResponse authenticate(JwtRequest request) throws InvalidCredentialsException;
}
