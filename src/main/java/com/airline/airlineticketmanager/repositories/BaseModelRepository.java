package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.BaseModel;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityExistsException;
import java.util.Date;
import java.util.Optional;

@NoRepositoryBean
public interface BaseModelRepository<T extends BaseModel, Long> extends JpaRepository<T, Long> {

    @Override
    default void deleteById(@NonNull Long id){
        Optional<T> byId = this.findById(id);
        if (byId.isPresent()){
            T entity = byId.get();
            if (entity.isDeleted()) throw new EntityExistsException("Entity with id "+ id +" currently deleted");
            entity.setDeleted(true);
            entity.setDeletedAt(new Date());
            this.save(entity);
        }
    }

    @Override
    default void delete(T entity){
        if (entity.isDeleted()) throw new EntityExistsException("Entity with id "+ entity.getId() +" currently deleted");
        entity.setDeleted(true);
        entity.setDeletedAt(new Date());
        this.save(entity);
    }
}
