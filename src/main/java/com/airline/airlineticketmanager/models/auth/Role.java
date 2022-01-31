package com.airline.airlineticketmanager.models.auth;

import com.airline.airlineticketmanager.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "roles")
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
