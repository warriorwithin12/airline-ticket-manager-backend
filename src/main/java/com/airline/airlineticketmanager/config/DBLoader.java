package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.repositories.PassengerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class DBLoader {

    @Bean
    CommandLineRunner initDatabase(
            PassengerRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(Passenger.builder()
                    .code("11111111L")
                    .firstName("Harry")
                    .lastName("Potter")
                    .email("hello@sample.com")
                    .address("London")
                    .build()));
            log.info("Preloading " + repository.save(Passenger.builder()
                    .code("22222222L")
                    .firstName("Ronald")
                    .lastName("Weasley")
                    .email("hello@sample.com")
                    .address("London")
                    .build()));
            log.info("Preloading " + repository.save(Passenger.builder()
                    .code("33333333L")
                    .firstName("Hermione")
                    .lastName("Granger")
                    .email("hello@sample.com")
                    .address("London")
                    .build()));
        };
    }
}
