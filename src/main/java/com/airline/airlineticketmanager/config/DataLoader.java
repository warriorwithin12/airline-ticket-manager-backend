package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.models.Plane;
import com.airline.airlineticketmanager.repositories.AirTicketRepository;
import com.airline.airlineticketmanager.repositories.FlightRepository;
import com.airline.airlineticketmanager.repositories.PassengerRepository;
import com.airline.airlineticketmanager.repositories.PlaneRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class DataLoader implements ApplicationRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dbMode;
    private final List<String> initDBModes;
    // JPA Repositories
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirTicketRepository airTicketRepository;

    public DataLoader(PassengerRepository passengerRepository, FlightRepository flightRepository, PlaneRepository planeRepository, AirTicketRepository airTicketRepository) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.airTicketRepository = airTicketRepository;
        this.initDBModes = Arrays.asList("create", "create-drop");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dbMode != null && initDBModes.contains(dbMode)) {
            this.loadPassengers();
            this.loadPlanes();
        }
    }

    private void loadPassengers(){
        log.info("Preloading passenger:" + passengerRepository.save(Passenger.builder()
                .code("11111111L")
                .firstName("Harry")
                .lastName("Potter")
                .email("hello@sample.com")
                .address("London")
                .build()));
        log.info("Preloading passenger: " + passengerRepository.save(Passenger.builder()
                .code("22222222L")
                .firstName("Ronald")
                .lastName("Weasley")
                .email("hello@sample.com")
                .address("London")
                .build()));
        log.info("Preloading passenger: " + passengerRepository.save(Passenger.builder()
                .code("33333333L")
                .firstName("Hermione")
                .lastName("Granger")
                .email("hello@sample.com")
                .address("London")
                .build()));
    }

    private void loadPlanes(){
        log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(180).model("Boeing 737-800").companyOwner("Vueling").build()));
        log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(299).model("Airbus A330-200").companyOwner("Vueling").build()));
    }
}
