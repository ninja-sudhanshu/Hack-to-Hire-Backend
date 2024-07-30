package indigo.hacktohire.app.dtos.airlines;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AirlineSignupRequest {
    @NotBlank(message = "Please provide a valid Airlines name")
    String name;

    String iata;

    String iaco;

    String callSign;

    String country;

    @NotBlank(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Please provide a valid password, Must be at least 8 characters")
    @Length(min = 8, message = "Please provide a valid password, Must be at least 8 characters")
    String password;

    String logo;
}