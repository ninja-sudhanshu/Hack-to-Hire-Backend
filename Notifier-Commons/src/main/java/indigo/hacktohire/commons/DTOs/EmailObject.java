package indigo.hacktohire.commons.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailObject {
    FlightStatus flightStatus;
    String pnrNumber;
    String recipientName;
    String recipientEmail;
    String flightNumber;
    String flightRoute;
    String flightDate;
    String flightStatusUpdatedTo;
    String customMessage;
}