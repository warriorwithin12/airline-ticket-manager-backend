package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.BaseModel;
import com.airline.airlineticketmanager.services.BaseService;
import com.airline.airlineticketmanager.utils.JsonMergePatchUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.NoSuchElementException;

/**
 * Base class for all rest controllers with basic CRUD operations:
 *  > Get, GetAll, Create, Update, Delete.
 *  
 * @param <T> Generic entity type subclass of BaseModel.
 * @param <ID> Type of identification field/column of type T (usually Long).
 */
@Log4j2
@RestController
public abstract class BaseCRUDRestController<T extends BaseModel, ID> {

    private final Class<T> currentEntityChildClass;

    /**
     * Stores a reference of subclass T used by childs of BaseCRUDRestController.
     * This reference is needed after when updating entities and build the merge-patch
     * to infer the data subtype of T.
     */
    @SuppressWarnings("unchecked")
    public BaseCRUDRestController() {
        this.currentEntityChildClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Get the service bean reference.
     * Child subclass of BaseCRUDRestController needs to override and
     * returns it's own implementation of service (ex: PassengerService).
     *
     * @return BaseService reference.
     */
    protected abstract BaseService<T, ID> getService();

    /**
     * Return single entity by it's id.
     *
     * @param id ID Identification field of child entity.
     * @return T entity object.
     */
    @GetMapping("/{id}")
    @ResponseBody
    T get(@PathVariable ID id){ return this.getService().getById(id);}

    /**
     * Get all elements in repository of type T.
     *
     * @return Iterable<T> collection of all elements.
     */
    @GetMapping("/list")
    @ResponseBody
    Iterable<T> getAll(){ return this.getService().getAll();}

    /**
     * Create an element of type T.
     *
     * @param target New entity element to create.
     * @return T entity created in repository.
     */
    @PostMapping
    @ResponseBody
    T create(@Validated @RequestBody T target){
        return this.getService().create(target);
    }

    /**
     * Update an element of type T specified by ID.
     * Makes a merge-patch with the JSON object from request.
     * Uses calculated entityModelClass child (T generic).
     *
     * @param id ID identification field of entity.
     * @param patch JsonMergePatch object with the modified entity.
     * @return T entity updated.
     * @throws JsonPatchException If fails merge-patch operation.
     */
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    @ResponseBody
    T update (@PathVariable ID id, @RequestBody JsonMergePatch patch) throws JsonPatchException {
        T exists = this.getService().getById(id);
        if (exists != null){
            T patched = JsonMergePatchUtils.mergePatch(patch, exists, currentEntityChildClass);
            return this.getService().update(patched);
        }
        return null;
    }

    /**
     * Delete an element of type T specified by ID.
     *
     * @param id ID identification field of entity,
     * @return T entity deleted.
     * @throws NoSuchElementException if not exists element T in repository.
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    T delete(@PathVariable ID id) throws NoSuchElementException {
        T deleted = this.getService().delete(id);
        if (deleted != null) return deleted;
        throw new NoSuchElementException("Not exist object with id: "+ id);
    }
}
