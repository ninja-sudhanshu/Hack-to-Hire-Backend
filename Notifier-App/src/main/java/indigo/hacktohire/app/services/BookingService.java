package indigo.hacktohire.app.services;

import indigo.hacktohire.app.dtos.booking.CreateBookingRequest;
import indigo.hacktohire.app.entities.Booking;
import indigo.hacktohire.app.entities.Flight;
import indigo.hacktohire.app.repositories.BookingRepositories;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepositories bookingRepositories;

    public Booking createBooking(CreateBookingRequest request){
        Booking booking = new Booking();
        BeanUtils.copyProperties(request, booking);

        booking.setPnr(Long.toString(Instant.now().getEpochSecond()));
        booking.setBookedOn(LocalDateTime.now());

        return bookingRepositories.save(booking);
    }

    public List<Booking> getAllPassengersByFlight(int flightId){
        Flight flight = new Flight();
        flight.setId(flightId);
        return bookingRepositories.findAllByFlight(flight);
    }

}