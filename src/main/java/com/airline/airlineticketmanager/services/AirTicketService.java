package com.airline.airlineticketmanager.services;

import com.airline.airlineticketmanager.models.AirTicket;
import com.airline.airlineticketmanager.repositories.AirTicketRepository;
import org.springframework.stereotype.Service;

@Service
public class AirTicketService extends BaseService<AirTicket, Long> {
    public AirTicketService(AirTicketRepository repository) {
        super(repository);
    }
}
