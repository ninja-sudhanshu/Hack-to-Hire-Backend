package indigo.hacktohire.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "passengers_records")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String pnr;

    @ManyToOne
    Flight flight;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    LocalDateTime bookedOn;
}