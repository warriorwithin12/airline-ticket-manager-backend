package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerService extends BaseService<Passenger, Long> {
    public PassengerService(PassengerRepository repository) {
        super(repository);
    }
}
