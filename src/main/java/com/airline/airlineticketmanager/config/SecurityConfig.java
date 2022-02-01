package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.services.UserDetailServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enables @PreAuthorize @PostAuthorize
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
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/manage/**", "/db-console/**")
            .permitAll()
                .antMatchers("/auth/**")
            .permitAll()
                .antMatchers(HttpMethod.GET, API_PATH + "/**").hasAnyRole("ADMIN", "API_READ", "API_WRITE")
                .antMatchers(HttpMethod.POST, API_PATH + "/**").hasAnyRole("ADMIN", "API_WRITE")
                .antMatchers(HttpMethod.PATCH, API_PATH + "/**").hasAnyRole("ADMIN", "API_WRITE")
                .antMatchers(HttpMethod.DELETE, API_PATH + "/**").hasAnyRole("ADMIN", "API_WRITE")
//                .antMatchers(API_PATH + "/**").hasRole("ADMIN")
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
