package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Flight extends BaseModel {

    @Column(length = 6, unique = true, nullable = false)
    @NonNull
    private String flightCode;

    @Column(length = 3)
    private String departure;

    @Column(length = 3)
    private String arrival;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date departureTime;

    @Temporal(TemporalType.TIME)
    private Date arrivalTime;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "plane_id")
    @OneToOne
    private Plane plane;

    @OneToMany(mappedBy = "flight")
    private Collection<Passenger> passengers;

}
