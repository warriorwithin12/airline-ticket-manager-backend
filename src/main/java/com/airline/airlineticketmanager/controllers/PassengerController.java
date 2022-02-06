package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.repositories.PassengerRepository;
import com.airline.airlineticketmanager.services.PassengerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${API_PATH}/passenger")
@Log4j2
public class PassengerController extends BaseCRUDRestController<Passenger, Long, PassengerRepository>{

    private final PassengerService passengerService;

    /**
     * Constructor.
     * Set all autowired fields (new in newer Java versions).
     *
     * @param passengerService The passenger service to operate abstractly with the JPA repository.
     */
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Install current implementation Service for type Passenger.
     * @return PassengerService subtype of BaseService.
     */
    @Override
    protected PassengerService getService(){
        return this.passengerService;
    }

}
