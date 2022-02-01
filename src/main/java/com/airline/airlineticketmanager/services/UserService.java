package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.auth.User;
import com.airline.airlineticketmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Long, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }
    public User getUserByUserName(String username){
        return this.repository.findByUsername(username);
    }
    public boolean existsByUsername(String username) {
        return this.repository.existsByUsername(username);
    }
    public boolean existsByEmail(String email){
        return this.repository.existsByEmail(email)
;    }
}
