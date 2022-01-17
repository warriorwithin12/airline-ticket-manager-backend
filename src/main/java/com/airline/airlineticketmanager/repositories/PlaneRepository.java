package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
