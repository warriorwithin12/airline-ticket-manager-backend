package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.services.PassengerService;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/passenger")
@Log4j2
public class PassengerController extends BaseCRUDRestController<Passenger, Long>{

    private final PassengerService passengerService;

    /**
     * Constructor.
     * Set all autowired fields (new in newer Java versions).
     *
     * @param passengerService The passenger service to operate abstractly with the JPA repository.
     */
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Install current implementation Service for type Passenger.
     * @return PassengerService subtype of BaseService.
     */
    @Override
    protected PassengerService getService(){
        return this.passengerService;
    }

    /**
     * Get all entities from DataStore.
     *
     * @return An iterable object (list, set, map, ...) with all entities from DataStore.
     */
    @Override
    public Iterable<Passenger> getAll(){
        return super.getAll();
    }

    /**
     * Get a single entity object with GET operation.
     *
     * @param id Current id from entity to retrieve.
     * @return Entity object from DataStore.
     */
    @Override
    public Passenger get(@PathVariable  Long id){
        return super.get(id);
    }

    /**
     * Creates new entity object with POST operation.
     *
     * @param passenger New entity object to create.
     * @return Entity object created in DataStore.
     */
    @Override
    public Passenger create(@Validated(Passenger.class) @RequestBody Passenger passenger){
        return super.create(passenger);
    }

    /**
     * Update fields from entity with PATCH operation.
     *
     * @param id Current id from entity to modify.
     * @param jsonMergePatch JSON object with fields modified of entity.
     * @return Entity object modified from DataStore.
     * @throws JsonPatchException If there are patching errors when merging new entity with old one.
     */
    @Override
    public Passenger update(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch, Class<Passenger> classReference) throws JsonPatchException {
        return super.update(id, jsonMergePatch, Passenger.class);
    }

    /**
     * Delete current entity specified by id with DELETE operation.
     *
     * @param id Current id from entity to delete.
     * @return Entity object deleted from DataStore.
     */
    @Override
    public Passenger delete(@PathVariable Long id){
        return super.delete(id);
    }
}
