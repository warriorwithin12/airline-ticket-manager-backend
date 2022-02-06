package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.services.UserDetailServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enables @PreAuthorize @PostAuthorize
@Log4j2
@Profile("local")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${API_PATH}")
    private String API_PATH;

    @Value("${AUTH_PATH}")
    private String AUTH;

    @Value("${authoritiesByUsernameQuery}")
    private String authoritiesByUsernameQuery;

    @Autowired
    private DataSource dataSource;

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .authoritiesByUsernameQuery(this.authoritiesByUsernameQuery);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/manage/**", "/db-console/**")
            .permitAll()
                .antMatchers(AUTH + "/register/**")
                    .hasAuthority(RoleValue.ROLE_ADMIN.name())
                .antMatchers(AUTH + "/**")
                    .permitAll()
                .antMatchers(HttpMethod.GET, API_PATH + "/**")
                    .hasAnyAuthority(RoleValue.ROLE_ADMIN.name(), RoleValue.ROLE_API_READ.name(), RoleValue.ROLE_API_WRITE.name())
                .antMatchers(HttpMethod.POST, API_PATH + "/**")
                    .hasAnyAuthority(RoleValue.ROLE_ADMIN.name(), RoleValue.ROLE_API_WRITE.name())
                .antMatchers(HttpMethod.PATCH, API_PATH + "/**")
                    .hasAnyAuthority(RoleValue.ROLE_ADMIN.name(), RoleValue.ROLE_API_WRITE.name())
                .antMatchers(HttpMethod.DELETE, API_PATH + "/**")
                    .hasAnyAuthority(RoleValue.ROLE_ADMIN.name(), RoleValue.ROLE_API_WRITE.name())
            .anyRequest().authenticated()
        .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .headers().frameOptions().sameOrigin()// for h2 DataStore
        .and()
            .cors()
        .and()
            .csrf().disable();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        log.warn("Loaded config SecurityConfig");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
