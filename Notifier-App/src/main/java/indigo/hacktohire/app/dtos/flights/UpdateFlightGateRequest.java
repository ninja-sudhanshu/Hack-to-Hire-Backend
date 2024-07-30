package indigo.hacktohire.app.dtos.flights;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateFlightGateRequest {
    @NotBlank(message = "Please provide a valid departure gate.")
    String departureGate;
}