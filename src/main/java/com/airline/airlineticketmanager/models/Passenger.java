package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "passenger")
@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Passenger extends BaseModel {

    @Column(unique = true)
    @NonNull
    private String code;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String address;

    @ManyToOne
    private Flight flight;

    @OneToMany(mappedBy = "ticketOwner")
    private Collection<AirTicket> boardingTickets;
}
