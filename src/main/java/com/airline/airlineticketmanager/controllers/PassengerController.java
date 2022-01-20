package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.services.PassengerService;
import com.airline.airlineticketmanager.utils.JsonMergePatchUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "${API_PATH}/passenger")
@Log4j2
public class PassengerController {

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
     * Get all entities from DataStore.
     *
     * @return An iterable object (list, set, map, ...) with all entities from DataStore.
     */
    @GetMapping("/list")
    public Iterable<Passenger> getAll(){
        return this.passengerService.getAll();
    }

    /**
     * Get a single entity object with GET operation.
     *
     * @param id Current id from entity to retrieve.
     * @return Entity object from DataStore.
     */
    @GetMapping("/{id}")
    public Passenger get(@PathVariable Long id){
        return this.passengerService.getById(id);
    }

    /**
     * Creates new entity object with POST operation.
     *
     * @param passenger New entity object to create.
     * @return Entity object created in DataStore.
     */
    @PostMapping
    @ResponseBody
    public Passenger create(@Validated(Passenger.class) @RequestBody Passenger passenger){
        return this.passengerService.create(passenger);
    }

    /**
     * Update fields from entity with PATCH operation.
     *
     * @param id Current id from entity to modify.
     * @param jsonMergePatch JSON object with fields modified of entity.
     * @return Entity object modified from DataStore.
     * @throws IOException If we cannot retrieve entity object from DataStore.
     * @throws JsonPatchException If there are patching errors when merging new entity with old one.
     */
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Passenger update(@PathVariable Long id, @RequestBody JsonMergePatch jsonMergePatch) throws IOException, JsonPatchException {
        Passenger exists = this.passengerService.getById(id);
        if(exists != null) {
            Passenger patched = JsonMergePatchUtils.mergePatch(jsonMergePatch, exists, Passenger.class);
            return this.passengerService.update(patched);
        }
        else {
            return null;
        }
    }

    /**
     * Delete current entity specified by id with DELETE operation.
     *
     * @param id Current id from entity to delete.
     * @return Entity object deleted from DataStore.
     */
    @DeleteMapping("/{id}")
    public Passenger delete(@PathVariable Long id){
        Passenger passenger = this.passengerService.delete(id);
        if (passenger != null){
            return passenger;
        }
        else {
            throw new NoSuchElementException("Not exist passenger with id: "+ id);
        }
    }
}
