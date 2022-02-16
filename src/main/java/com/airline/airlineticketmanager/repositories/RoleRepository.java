package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.auth.Role;

public interface RoleRepository extends BaseModelRepository<Role, Long> {
    Role findByName(String name);
}
