package indigo.hacktohire.app.dtos.flights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelFlightRequest {
    String message;
}