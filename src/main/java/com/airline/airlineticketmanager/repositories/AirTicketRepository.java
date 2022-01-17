package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.AirTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirTicketRepository extends JpaRepository<AirTicket, Long> {
}
