package com.airline.airlineticketmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AirlineTicketManagerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineTicketManagerBackendApplication.class, args);
    }
}
