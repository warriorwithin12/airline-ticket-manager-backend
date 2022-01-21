package com.airline.airlineticketmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airline")
@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Airline extends BaseModel {

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
    @JsonIgnoreProperties("owner")
    private List<Plane> planes;

}
