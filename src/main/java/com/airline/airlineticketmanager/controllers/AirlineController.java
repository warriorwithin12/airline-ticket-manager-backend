package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Airline;
import com.airline.airlineticketmanager.services.AirlineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/airline")
public class AirlineController extends BaseCRUDRestController<Airline, Long> {

    private final AirlineService service;

    public AirlineController(AirlineService service) {
        this.service = service;
    }

    @Override
    protected AirlineService getService() {
        return this.service;
    }
}
