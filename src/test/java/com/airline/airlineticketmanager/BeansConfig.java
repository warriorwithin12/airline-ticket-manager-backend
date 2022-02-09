package com.airline.airlineticketmanager;

import com.airline.airlineticketmanager.services.RoleService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class BeansConfig {

    @Bean @Primary
    public RoleService roleService(){
        return Mockito.mock(RoleService.class);
    }
}
