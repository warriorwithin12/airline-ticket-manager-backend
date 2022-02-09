package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, Long, RoleRepository> {

    public RoleService(RoleRepository repository) {
        super(repository);
    }
    public Role getRoleByName(String name){
        return this.repository.findByName(name);
    }
    public String sayHello(){ return "Hello!"; }
}
