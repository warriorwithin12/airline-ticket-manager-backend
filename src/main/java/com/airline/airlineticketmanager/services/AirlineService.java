package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.Airline;
import com.airline.airlineticketmanager.repositories.AirlineRepository;
import org.springframework.stereotype.Service;

@Service
public class AirlineService extends BaseService<Airline, Long, AirlineRepository> {
    public AirlineService(AirlineRepository repository) {
        super(repository);
    }
}
