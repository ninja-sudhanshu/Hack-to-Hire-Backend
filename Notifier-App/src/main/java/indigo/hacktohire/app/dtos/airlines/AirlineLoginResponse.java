package indigo.hacktohire.app.dtos.airlines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AirlineLoginResponse {
    String accessToken;
}