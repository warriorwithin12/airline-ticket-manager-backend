package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;

@Entity
@Table(name = "plane")
@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Plane extends BaseModel {

    @Column(nullable = false)
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

}
