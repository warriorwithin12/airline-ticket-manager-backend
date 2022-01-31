package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String nane);
}
