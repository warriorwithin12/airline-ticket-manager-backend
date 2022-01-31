package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.services.UserDetailServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Log4j2
@Profile("local")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${API_PATH}")
    private String API_PATH;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/manage/**", "/db-console/**")
                .permitAll()// .hasRole("SYS_ADMIN")
            .antMatchers(API_PATH + "/**")
//                .hasAnyRole("ADMIN","USER")
                .hasRole("ADMIN")
                .anyRequest().authenticated()
        .and()
            .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .headers().frameOptions().sameOrigin()// for h2 DataStore
        .and()
            .cors()
        .and()
            .csrf().disable();
        log.warn("Loaded config BasicHttpAuthSecurityConfig");
    }
}
