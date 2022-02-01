package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleValue name);
}
