package indigo.hacktohire.app.repositories;

import indigo.hacktohire.app.entities.Airline;
import indigo.hacktohire.app.entities.Flight;
import indigo.hacktohire.commons.DTOs.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepositories extends JpaRepository<Flight, Integer> {
    List<Flight> findByAirline(Airline airline);

    List<Flight> findAllByDepartureDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Modifying
    @Query(value = "UPDATE flights SET flight_status = ?1 WHERE id = ?2 AND airline_id = ?3", nativeQuery = true)
    int updateFlightStatusByIdAndAirline(FlightStatus flightStatus, int flightId, int airlineId);

    @Modifying
    @Query(value = "UPDATE flights SET flight_status = ?1, departure_date_time = ?2, arrival_date_time = ?3  WHERE id = ?4 AND airline_id = ?5", nativeQuery = true)
    int updateFlightDelayStatus(FlightStatus flightStatus, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, int flightId, int airlineId);

    @Modifying
    @Query(value = "UPDATE flights SET departure_gate = ?1 WHERE id = ?2 AND airline_id = ?3", nativeQuery = true)
    int updateFlightGate(String departureGate, int flightId, int airlineId);
}