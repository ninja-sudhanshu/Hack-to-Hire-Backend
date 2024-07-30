package indigo.hacktohire.app.dtos.booking;

import indigo.hacktohire.app.entities.Flight;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBookingRequest {
    @NotNull(message = "Please book ticket for a valid flight.")
    Flight flight;

    @NotBlank(message = "Please enter a valid first name.")
    String firstName;

    @NotBlank(message = "Please enter a valid last name.")
    String lastName;

    @NotBlank(message = "Please enter a valid email.")
    String email;

    @NotBlank(message = "Please enter a valid phone number.")
    String phoneNumber;
}
