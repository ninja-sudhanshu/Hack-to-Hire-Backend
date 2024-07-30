package indigo.hacktohire.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "airlines")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String iata;

    String iaco;

    String callSign;

    String country;

    @Column(unique = true)
    String email;

    String password;

    String logo;
}