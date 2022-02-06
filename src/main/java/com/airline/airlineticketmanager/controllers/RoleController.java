package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${AUTH_PATH}/role")
@Log4j2
public class RoleController extends BaseCRUDRestController<Role, Long, RoleRepository> {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    protected RoleService getService() {
        return this.roleService;
    }


}
