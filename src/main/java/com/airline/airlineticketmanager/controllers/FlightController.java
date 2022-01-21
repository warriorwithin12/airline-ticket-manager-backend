package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Flight;
import com.airline.airlineticketmanager.services.FlightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/flight")
public class FlightController extends BaseCRUDRestController<Flight, Long> {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @Override
    protected FlightService getService() {
        return this.service;
    }
}
