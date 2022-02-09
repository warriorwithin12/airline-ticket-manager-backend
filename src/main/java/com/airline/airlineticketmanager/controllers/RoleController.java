package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.RoleService;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
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

    /**
     * Creates new Role object in DB and
     * add the new Role to RoleValue enum.
     *
     * @param target New entity element to create.
     * @return Role created.
     */
    @Override
    Role create(Role target) {
        Role newRole = super.create(target);
        RoleValue.putValue(target.getName());
        return newRole;
    }

    /**
     * Modifies an existing Role from DB.
     * Adds or updates de RoleValue enum.
     *
     * @param id ID identification field of entity.
     * @param patch JsonMergePatch object with the modified entity.
     * @return Role modified.
     * @throws JsonPatchException If fails merge PATCH.
     */
    @Override
    Role update(Long id, JsonMergePatch patch) throws JsonPatchException {
        Role modifiedRole = super.update(id, patch);
        RoleValue.putValue(modifiedRole.getName());
        return modifiedRole;
    }
}
