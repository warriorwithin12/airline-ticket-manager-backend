package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airline")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String company;

    private String companyName;

    @Column(nullable = false)
    @NonNull
    private String country;

    @Column(length = 2, nullable = false)
    @NonNull
    private String countryCode;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Plane> planes;

}
