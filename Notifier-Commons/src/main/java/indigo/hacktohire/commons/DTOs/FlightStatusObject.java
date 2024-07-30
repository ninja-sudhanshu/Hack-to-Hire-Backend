package indigo.hacktohire.commons.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightStatusObject {
    String time;
    String logo_url;
    String airline;
    String flightNumber;
    String destination;
    String gate;
    String status;
}