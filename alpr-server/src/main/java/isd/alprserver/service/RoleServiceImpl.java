package isd.alprserver.service;

import isd.alprserver.model.Role;
import isd.alprserver.model.exceptions.RoleNotFoundException;
import isd.alprserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) throws RoleNotFoundException {
        return roleRepository.findByName(name).orElseThrow(() ->
                new RoleNotFoundException("This role doesn't exist"));
    }
}
