package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T extends BaseModel, ID> {

    JpaRepository<T, ID> repository;

    public BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T getById(ID id) {
        return this.repository.findById(id).orElse(null);
    }

    public Iterable<T> getAll(){
        return this.repository.findAll();
    }

    public T create(T newer) {
        if (this.validate(newer)){
            return this.repository.save(newer);
        }
        throw new IllegalArgumentException("The object with type T is not a valid: "+ newer.toString());
    }

    public T update(T modified) {
        if (this.validate(modified))
            return this.repository.save(modified);
        throw new IllegalArgumentException("Cannot save modified object. Validation not successful: "+ modified.toString());
    }

    public T delete(ID id) {
        T target = this.getById(id);
        if (target != null) {
            this.repository.deleteById(id);
            return target;
        }
        throw new IllegalArgumentException("Cannot delete null object");
    }

    boolean validate(T target)  {
        return target != null;
    }
}
