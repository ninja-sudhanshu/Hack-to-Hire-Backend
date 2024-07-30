package indigo.hacktohire.commons.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WhatsappObject {
    FlightStatus flightStatus;
    String pnrNumber;
    String recipientName;
    String recipientNumber;
    String flightNumber;
    String flightRoute;
    String flightDate;
    String flightStatusUpdatedTo;
    String customMessage;
}
