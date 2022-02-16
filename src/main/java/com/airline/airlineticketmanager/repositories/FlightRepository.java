package com.airline.airlineticketmanager.repositories;

import com.airline.airlineticketmanager.models.Flight;
import com.airline.airlineticketmanager.models.FlightStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface FlightRepository extends BaseModelRepository<Flight, Long> {

    Iterable<Flight> findAllByDate(Date date);
    Iterable<Flight> findAllByModifiedDateTime(Date date);
    Iterable<Flight> findAllByDepartureDateTimeBetween(Date start, Date end);
    Iterable<Flight> findAllByArrivalDateTimeBetween(Date start, Date end);

    @Query("select f from Flight f where f.status = :status")
    Iterable<Flight> findAllByStatus(@Param("status") FlightStatus status);

    default Iterable<Flight> getAllFlightsOnAir(){
        return this.findAllByStatus(FlightStatus.ON_AIR);
    }
    default Iterable<Flight> getAllFlightsCancelled(){
        return this.findAllByStatus(FlightStatus.CANCELLED);
    }
    default Iterable<Flight> getAllFlightsBoarding(){
        return this.findAllByStatus(FlightStatus.BOARDING);
    }
    default Iterable<Flight> getAllFlightsWaiting(){
        return this.findAllByStatus(FlightStatus.WAITING);
    }
    default Iterable<Flight> getAllFlightsArriving(){
        return this.findAllByStatus(FlightStatus.ARRIVING);
    }
    default Iterable<Flight> getAllFlightsDelayed(){
        return this.findAllByStatus(FlightStatus.DELAYED);
    }
    default Iterable<Flight> getAllFlightsTakingOff(){
        return this.findAllByStatus(FlightStatus.TAKING_OFF);
    }
}
