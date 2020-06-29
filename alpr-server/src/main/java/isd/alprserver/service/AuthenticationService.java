package isd.alprserver.service;

import isd.alprserver.model.JwtRequest;
import isd.alprserver.model.JwtResponse;
import isd.alprserver.model.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    JwtResponse authenticate(JwtRequest request) throws InvalidCredentialsException;
}
