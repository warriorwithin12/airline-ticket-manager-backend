package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.AirlineTicketManagerBackendApplication;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AirlineTicketManagerBackendApplication.class);
    }
}
