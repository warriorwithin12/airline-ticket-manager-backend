package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.repositories.PassengerRepository;
import com.airline.airlineticketmanager.utils.JsonMergePatchUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "${API_PATH}/passenger")
@Log4j2
public class PassengerController {

    private final PassengerRepository passengerRepository;

    /**
     * Constructor.
     * Set all autowired fields (new in newer Java versions).
     *
     * @param passengerRepository The passenger JPA repository to operate with CRUD actions.
     */
    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    /**
     * Get all entities from DataStore.
     *
     * @return An iterable object (list, set, map, ...) with all entities from DataStore.
     */
    @GetMapping("/list")
    public Iterable<Passenger> getAll(){
        return this.passengerRepository.findAll();
    }

    /**
     * Get a single entity object with GET operation.
     *
     * @param id Current id from entity to retrieve.
     * @return Entity object from DataStore.
     */
    @GetMapping("/{id}")
    public Passenger get(@PathVariable Long id){
        return this.passengerRepository.findById(id).orElseGet(() -> null);
    }

    /**
     * Creates new entity object with POST operation.
     *
     * @param passenger New entity object to create.
     * @return Entity object created in DataStore.
     */
    @PostMapping
    @ResponseBody
    public Passenger create(@RequestBody Passenger passenger){
//        if (passengerValidator.validate(passenger)){
            Passenger newPassenger = this.passengerRepository.saveAndFlush(passenger);
            log.info("Created passenger: {}", newPassenger.toString());
            return newPassenger;
//        }
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
        Optional<Passenger> exists = this.passengerRepository.findById(id);
        if(exists.isPresent()) {
            Passenger patched = JsonMergePatchUtils.mergePatch(jsonMergePatch, exists.get(), Passenger.class);
            patched = this.passengerRepository.saveAndFlush(patched);
            log.info("Patched Passenger object :: {}", patched.toString());
            return patched;
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
        Optional<Passenger> passenger = this.passengerRepository.findById(id);
        if (passenger.isPresent()){
            Passenger targetToDelete = passenger.get();
            this.passengerRepository.delete(targetToDelete);
            log.info("Deleted Passenger object :: {}", targetToDelete.toString());
            return targetToDelete;
        }
        else {
            throw new NoSuchElementException("Not exist passenger with id: "+ id);
        }
    }
}
