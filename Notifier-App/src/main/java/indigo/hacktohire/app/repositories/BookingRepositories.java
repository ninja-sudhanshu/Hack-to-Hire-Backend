package indigo.hacktohire.app.repositories;

import indigo.hacktohire.app.entities.Booking;
import indigo.hacktohire.app.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepositories extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByFlight(Flight flight);
}