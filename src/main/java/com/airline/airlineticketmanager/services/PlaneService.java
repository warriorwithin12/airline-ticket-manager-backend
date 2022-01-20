package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.Plane;
import com.airline.airlineticketmanager.repositories.PlaneRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaneService extends BaseService<Plane, Long> {

    public PlaneService(PlaneRepository repository) {
        super(repository);
    }

    @Override
    boolean validate(Plane plane) {
        return super.validate(plane);
    }
}
