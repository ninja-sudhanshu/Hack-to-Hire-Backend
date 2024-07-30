package indigo.hacktohire.app.services;

import indigo.hacktohire.app.dtos.airlines.AirlineLoginRequest;
import indigo.hacktohire.app.dtos.airlines.AirlineLoginResponse;
import indigo.hacktohire.app.dtos.airlines.AirlineSignupRequest;
import indigo.hacktohire.app.dtos.airlines.AirlineSignupResponse;
import indigo.hacktohire.app.entities.Airline;
import indigo.hacktohire.app.exceptions.EntityNotFoundException;
import indigo.hacktohire.app.repositories.AirlineRepositories;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirlineService {

    @Autowired
    AirlineRepositories airlineRepositories;

    @Autowired
    Utility utility;

    @Autowired
    JwtService jwtService;

    public AirlineSignupResponse create(AirlineSignupRequest request) throws DataIntegrityViolationException {
        Airline airline = new Airline();
        BeanUtils.copyProperties(request, airline);
        airline.setPassword(utility.toMD5(airline.getPassword()));
        airline = airlineRepositories.save(airline);

        AirlineSignupResponse airlineSignupResponse = new AirlineSignupResponse();
        BeanUtils.copyProperties(airline, airlineSignupResponse);

        return airlineSignupResponse;
    }

    public AirlineLoginResponse login(AirlineLoginRequest request) throws EntityNotFoundException {
        Optional<Airline> optionalAirline = airlineRepositories.findByEmail(request.getEmail());
        if(optionalAirline.isPresent()){
            if(optionalAirline.get().getPassword().equals(utility.toMD5(request.getPassword()))){
                AirlineLoginResponse airlineLoginResponse = new AirlineLoginResponse();
                airlineLoginResponse.setAccessToken(jwtService.issueJWT(optionalAirline.get()));
                return airlineLoginResponse;
            }
            throw new EntityNotFoundException("Wrong password, Please try again.");
        }
        throw new EntityNotFoundException("Could not find any airline with email "+request.getEmail());
    }

}
