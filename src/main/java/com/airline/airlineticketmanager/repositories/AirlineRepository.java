package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.Airline;

public interface AirlineRepository extends BaseModelRepository<Airline, Long> {
    Airline findByCompany(String company);
}
