package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PLANE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private int capacity;

    @NonNull
    private String model;

    @OneToMany(mappedBy = "plane")
    private Set<Flight> flights;

    public  void removeFlight(Flight flight){
        flights.remove(flight);
    }
}
