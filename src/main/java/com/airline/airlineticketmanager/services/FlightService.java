package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.Flight;
import com.airline.airlineticketmanager.repositories.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightService extends BaseService<Flight, Long> {
    public FlightService(FlightRepository repository) {
        super(repository);
    }
}
