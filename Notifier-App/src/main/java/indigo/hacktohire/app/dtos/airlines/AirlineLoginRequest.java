package indigo.hacktohire.app.dtos.airlines;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AirlineLoginRequest {
    @NotBlank(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Please provide a valid password")
    @Length(min = 8, message = "Please provide a valid password")
    String password;
}