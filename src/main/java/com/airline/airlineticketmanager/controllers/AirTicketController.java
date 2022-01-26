package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.AirTicket;
import com.airline.airlineticketmanager.services.AirTicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/airticket")
public class AirTicketController extends BaseCRUDRestController<AirTicket, Long> {

    private final AirTicketService service;

    public AirTicketController(AirTicketService service) {
        this.service = service;
    }

    @Override
    protected AirTicketService getService() {
        return this.service;
    }

}
