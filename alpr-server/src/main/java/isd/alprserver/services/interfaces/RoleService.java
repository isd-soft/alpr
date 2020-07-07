package isd.alprserver.services.interfaces;

import isd.alprserver.model.Role;
import isd.alprserver.model.exceptions.RoleNotFoundException;

public interface RoleService {
    Role getByName(String name) throws RoleNotFoundException;
}
