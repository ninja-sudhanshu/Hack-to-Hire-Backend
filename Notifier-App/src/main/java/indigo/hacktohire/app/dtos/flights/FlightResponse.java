package indigo.hacktohire.app.dtos.flights;

import indigo.hacktohire.app.dtos.airlines.AirlineResponse;
import indigo.hacktohire.commons.DTOs.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightResponse {
    int id;
    String flightNumber;
    String flightType;
    FlightStatus flightStatus = FlightStatus.ON_TIME;
    AirlineResponse airline;
    String departureAirport;
    LocalDateTime departureDateTime;
    String departureGate;
    String arrivalAirport;
    LocalDateTime arrivalDateTime;
    String arrivalGate;
}
