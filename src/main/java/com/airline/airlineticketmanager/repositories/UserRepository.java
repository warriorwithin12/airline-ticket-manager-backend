package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.auth.User;

public interface UserRepository extends BaseModelRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
