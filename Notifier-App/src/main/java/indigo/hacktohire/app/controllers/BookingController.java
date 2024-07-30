package indigo.hacktohire.app.controllers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.dtos.booking.CreateBookingRequest;
import indigo.hacktohire.app.dtos.flights.CreateFlightRequest;
import indigo.hacktohire.app.dtos.flights.FlightResponse;
import indigo.hacktohire.app.entities.Booking;
import indigo.hacktohire.app.security.UserPrincipal;
import indigo.hacktohire.app.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping(path = "")
    public ResponseEntity<APIResponse<Booking>> createBooking(@Validated @RequestBody CreateBookingRequest request) {
        APIResponse<Booking> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(bookingService.createBooking(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}