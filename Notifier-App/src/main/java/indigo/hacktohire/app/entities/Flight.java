package indigo.hacktohire.app.entities;

import indigo.hacktohire.commons.DTOs.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String flightNumber;
    String flightType;
    FlightStatus flightStatus = FlightStatus.ON_TIME;

    @ManyToOne(fetch = FetchType.EAGER)
    Airline airline;

    String departureAirport;
    LocalDateTime departureDateTime;
    String departureGate;

    String arrivalAirport;
    LocalDateTime arrivalDateTime;
    String arrivalGate;
}