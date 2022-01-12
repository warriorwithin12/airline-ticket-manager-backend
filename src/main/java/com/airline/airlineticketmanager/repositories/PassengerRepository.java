package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
