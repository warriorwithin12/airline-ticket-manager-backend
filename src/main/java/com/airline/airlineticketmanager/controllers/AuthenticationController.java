package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.auth.User;
import com.airline.airlineticketmanager.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/auth")
@Log4j2
public class AuthenticationController {

    private final UserService service;

    public AuthenticationController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    User login(Authentication authentication){
        return this.service.getUserByUserName(authentication.getName());
    }

    @GetMapping("/logout")
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            new SecurityContextLogoutHandler().logout(request, response, auth);
            log.info("User {} logout!", currentUserName);
        }
        else {
            throw new AuthenticationCredentialsNotFoundException("Error in logout process. Not found active authentication.");
        }
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setContentType("application/json");
        response.getWriter().write("User logout.");
    }
}
