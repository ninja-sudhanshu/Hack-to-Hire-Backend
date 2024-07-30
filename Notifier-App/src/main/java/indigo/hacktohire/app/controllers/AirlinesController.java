package indigo.hacktohire.app.controllers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.airlines.AirlineLoginRequest;
import indigo.hacktohire.app.dtos.airlines.AirlineLoginResponse;
import indigo.hacktohire.app.dtos.airlines.AirlineSignupRequest;
import indigo.hacktohire.app.dtos.airlines.AirlineSignupResponse;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.exceptions.EntityNotFoundException;
import indigo.hacktohire.app.services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/airlines")
public class AirlinesController {

    @Autowired
    AirlineService airlineService;

    @PostMapping(path = "/signup")
    public ResponseEntity<APIResponse<AirlineSignupResponse>> createAirlines(@Validated @RequestBody AirlineSignupRequest request) throws DataIntegrityViolationException {
        APIResponse<AirlineSignupResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(airlineService.create(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<APIResponse<AirlineLoginResponse>> loginAirlines(@Validated @RequestBody AirlineLoginRequest request) throws EntityNotFoundException {
        APIResponse<AirlineLoginResponse> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.SUCCESS);
        apiResponse.setData(airlineService.login(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}