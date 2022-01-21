package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.BaseModel;
import com.airline.airlineticketmanager.services.BaseService;
import com.airline.airlineticketmanager.utils.JsonMergePatchUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public abstract class BaseCRUDRestController<T extends BaseModel, ID> {

    protected abstract BaseService<T, ID> getService();

    @GetMapping("/{id}")
    T get(@PathVariable ID id){ return this.getService().getById(id);}

    @GetMapping("/list")
    Iterable<T> getAll(){ return this.getService().getAll();}

    @PostMapping @ResponseBody
    T create(@RequestBody T target){ return this.getService().create(target); }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    T update (@PathVariable ID id, @RequestBody JsonMergePatch patch, Class<T> classReference) throws JsonPatchException{
        T exists = this.getService().getById(id);
        if (exists != null){
            T patched = JsonMergePatchUtils.mergePatch(patch, exists, classReference);
            return this.getService().update(patched);
        }
        return null;
    }

    @DeleteMapping("/{id}") T delete(@PathVariable ID id){
        T deleted = this.getService().delete(id);
        if (deleted != null) return deleted;
        throw new NoSuchElementException("Not exist object with id: "+ id);
    }
}
