package indigo.hacktohire.app.dtos.airlines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AirlineResponse {
    int id;
    String name;
    String iata;
    String iaco;
    String callSign;
    String country;
    String logo;
}
