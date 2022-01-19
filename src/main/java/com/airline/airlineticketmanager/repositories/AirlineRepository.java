package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Airline findByCompany(String company);
}
