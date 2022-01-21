package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Plane;
import com.airline.airlineticketmanager.services.PlaneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/plane")
public class PlaneController extends BaseCRUDRestController<Plane, Long>{

    private final PlaneService service;

    public PlaneController(PlaneService service) {
        this.service = service;
    }

    @Override
    protected PlaneService getService() {
        return this.service;
    }
}
