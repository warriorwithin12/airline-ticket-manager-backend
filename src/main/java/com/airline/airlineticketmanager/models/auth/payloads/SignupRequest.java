package com.airline.airlineticketmanager.models.auth.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SignupRequest {

    @NotBlank
//    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
//    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
//    @Size(min = 6, max = 40)
    private String password;
}
