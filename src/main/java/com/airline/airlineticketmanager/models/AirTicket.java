package com.airline.airlineticketmanager.models;

import com.airline.airlineticketmanager.utils.ReservationCodeGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "air_ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class AirTicket extends BaseModel {

    private UUID reservationId;

    @ManyToOne
    private Passenger ticketOwner;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date purchasedTime;

    @NonNull
    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 5, fraction=2)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "%.2f")
    private BigDecimal totalAmount;

    @Digits(integer = 5, fraction=2)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "%.2f")
    @Builder.Default
    private BigDecimal taxes = BigDecimal.ZERO;

    @Digits(integer = 5, fraction=2)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "%.2f")
    @Builder.Default
    private BigDecimal complements = BigDecimal.ZERO;

    @NonNull
    @Column(nullable = false, length = 4)
    private String seat;

    @NonNull
    @Column(nullable = false, length = 2)
    private String terminal;

    @PrePersist
    protected void onCreate() {
//        setReservationId(ReservationCodeGenerator.generateRandomString());
        setReservationId(ReservationCodeGenerator.generateUUID());
    }

}
