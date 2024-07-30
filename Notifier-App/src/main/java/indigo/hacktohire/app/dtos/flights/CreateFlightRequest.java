package indigo.hacktohire.app.dtos.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateFlightRequest {

    @NotBlank(message = "Please provide a valid Flight number")
    String flightNumber;

    @NotBlank(message = "Please provide a valid Flight name")
    String flightType;

    @NotBlank(message = "Please provide a valid IATA code for Departure airport")
    String departureAirport;

    @NotNull(message = "Please provide a valid Departure date and time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime departureDateTime;

    String departureGate;

    @NotBlank(message = "Please provide a valid IATA code for Arrival airport")
    String arrivalAirport;

    @NotNull(message = "Please provide a valid Arrival date and time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime arrivalDateTime;

    String arrivalGate;
}