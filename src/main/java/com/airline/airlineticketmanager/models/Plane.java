package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;

@Entity
@Table(name = "plane")
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

    @Column(nullable = false)
    @NonNull
    private int capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PlaneCapacityType capacityType = PlaneCapacityType.PAX;

    @Column(nullable = false)
    @NonNull
    private String model;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Airline owner;

//    @OneToMany(mappedBy = "plane")
//    private Set<Flight> flights;

//    public  void removeFlight(Flight flight){
//        flights.remove(flight);
//    }
}
