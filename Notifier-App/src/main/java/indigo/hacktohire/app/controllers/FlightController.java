package indigo.hacktohire.app.controllers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.dtos.flights.*;
import indigo.hacktohire.app.exceptions.EntityNotFoundException;
import indigo.hacktohire.app.security.UserPrincipal;
import indigo.hacktohire.app.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping(path = "")
    public ResponseEntity<APIResponse<FlightResponse>> createFlight(@AuthenticationPrincipal UserPrincipal userPrincipal, @Validated @RequestBody CreateFlightRequest request) throws DataIntegrityViolationException {
        APIResponse<FlightResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.create(request, userPrincipal.getAirline()));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public ResponseEntity<APIResponse<List<FlightResponse>>> getFlights(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        APIResponse<List<FlightResponse>> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.getAllFlightsByAirline(userPrincipal.getAirline()));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<APIResponse<List<FlightResponse>>> getAllFlights() {
        APIResponse<List<FlightResponse>> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.getAllFlights());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping(path = "/{flightId}/delay")
    public ResponseEntity<APIResponse<FlightResponse>> delayFlight(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable int flightId, @Validated @RequestBody DelayFlightRequest request) throws EntityNotFoundException {
        APIResponse<FlightResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.updateFlightStatusAsDelayed(flightId, userPrincipal.getAirline(), request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping(path = "/{flightId}/cancel")
    public ResponseEntity<APIResponse<FlightResponse>> cancelFlight(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable int flightId, @RequestBody CancelFlightRequest request) throws EntityNotFoundException {
        APIResponse<FlightResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.updateFlightStatusAsCancelled(flightId, userPrincipal.getAirline(), request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping(path = "/{flightId}/gate-change")
    public ResponseEntity<APIResponse<FlightResponse>> changeGate(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable int flightId, @Validated @RequestBody UpdateFlightGateRequest request) throws EntityNotFoundException {
        APIResponse<FlightResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(flightService.updateFlightGate(flightId, userPrincipal.getAirline(), request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}