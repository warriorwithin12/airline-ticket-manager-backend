package com.airline.airlineticketmanager.controllers;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.models.auth.User;
import com.airline.airlineticketmanager.models.auth.payloads.LoginRequest;
import com.airline.airlineticketmanager.models.auth.payloads.MessageResponse;
import com.airline.airlineticketmanager.models.auth.payloads.SignupRequest;
import com.airline.airlineticketmanager.models.auth.payloads.UserInfoResponse;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.UserService;
import com.airline.airlineticketmanager.utils.JWTUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/auth")
@Log4j2
public class AuthenticationController {

    private final UserService service;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JWTUtils jwtUtils;

    public AuthenticationController(UserService service, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder encoder, JWTUtils jwtUtils) {
        this.service = service;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        User currentUser = this.service.getUserByUserName(userDetails.getUsername());
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail(), roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/register")
    @ExceptionHandler(InvalidAttributeValueException.class)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) throws InvalidAttributeValueException {
        if (service.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (service.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = User.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = strRoles.stream().map(role -> roleRepository.findByName(RoleValue.valueOf(role))).collect(Collectors.toSet());
        user.setRoles(roles);
        service.create(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
