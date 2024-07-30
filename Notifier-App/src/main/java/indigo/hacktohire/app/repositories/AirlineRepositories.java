package indigo.hacktohire.app.repositories;

import indigo.hacktohire.app.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepositories extends JpaRepository<Airline, Integer> {
    Optional<Airline> findByEmail(String email);
}
