package indigo.hacktohire.app.services;

import indigo.hacktohire.app.dtos.airlines.AirlineResponse;
import indigo.hacktohire.app.dtos.flights.*;
import indigo.hacktohire.app.entities.Airline;
import indigo.hacktohire.app.entities.Booking;
import indigo.hacktohire.app.entities.Flight;
import indigo.hacktohire.app.exceptions.EntityNotFoundException;
import indigo.hacktohire.app.repositories.FlightRepositories;
import indigo.hacktohire.commons.DTOs.FlightStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class FlightService {

    @Autowired
    FlightRepositories flightRepositories;

    @Autowired
    BookingService bookingService;

    @Autowired
    KafkaEmailProducer kafkaEmailProducer;

    @Autowired
    KafkaWhatsappProducer kafkaWhatsappProducer;

    @Autowired
    KafkaFlightDepartureStatusProducer kafkaFlightDepartureStatusProducer;

    @Autowired
    Utility utility;

    public FlightResponse create(CreateFlightRequest request, Airline airline){
        Flight flight = new Flight();
        BeanUtils.copyProperties(request, flight);
        flight.setAirline(airline);
        flight = flightRepositories.save(flight);

        FlightResponse flightResponse = new FlightResponse();
        BeanUtils.copyProperties(flight, flightResponse);
        return flightResponse;
    }

    public List<FlightResponse> getAllFlights(){
        List<Flight> flightList = flightRepositories.findAll();
        List<FlightResponse> flightResponseList = new ArrayList<>();
        for(int i=0; i<flightList.size(); i++){
            FlightResponse flightResponse = new FlightResponse();
            BeanUtils.copyProperties(flightList.get(i), flightResponse);

            AirlineResponse airlineResponse = new AirlineResponse();
            BeanUtils.copyProperties(flightList.get(i).getAirline(), airlineResponse);
            flightResponse.setAirline(airlineResponse);

            flightResponseList.add(flightResponse);
        }

        Comparator<FlightResponse> comparator = new Comparator<FlightResponse>() {
            @Override
            public int compare(FlightResponse left, FlightResponse right) {
                return left.getId() - right.getId();
            }
        };
        Collections.sort(flightResponseList, comparator);
        return flightResponseList;
    }

    public List<FlightResponse> getAllFlightsByAirline(Airline airline){
        List<Flight> flightList = flightRepositories.findByAirline(airline);
        List<FlightResponse> flightResponseList = new ArrayList<>();
        for(int i=0; i<flightList.size(); i++){
            FlightResponse flightResponse = new FlightResponse();
            BeanUtils.copyProperties(flightList.get(i), flightResponse);

            AirlineResponse airlineResponse = new AirlineResponse();
            BeanUtils.copyProperties(flightList.get(i).getAirline(), airlineResponse);
            flightResponse.setAirline(airlineResponse);

            flightResponseList.add(flightResponse);
        }
        return flightResponseList;
    }

    @Transactional
    public FlightResponse updateFlightStatusAsDelayed(int flightId, Airline airline, DelayFlightRequest request) throws EntityNotFoundException {
        int isQuerySuccess = flightRepositories.updateFlightDelayStatus(FlightStatus.RESCHEDULED, request.getDepartureDateTime(), request.getArrivalDateTime(), flightId, airline.getId());
        Optional<Flight> optionalFlight = flightRepositories.findById(flightId);
        if(isQuerySuccess == 1  && optionalFlight.isPresent()){
            AirlineResponse airlineResponse = new AirlineResponse();
            BeanUtils.copyProperties(optionalFlight.get().getAirline(), airlineResponse);

            FlightResponse flightResponse = new FlightResponse();
            BeanUtils.copyProperties(optionalFlight.get(), flightResponse);

            flightResponse.setAirline(airlineResponse);
            notifyFlightStatusUpdate(
                    optionalFlight.get().getId(),
                    indigo.hacktohire.commons.DTOs.FlightStatus.RESCHEDULED,
                    Time.from(optionalFlight.get().getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString(),
                    request.getMessage()
                    );
            return flightResponse;
        }
        throw new EntityNotFoundException("Oops!! Cannot delay flight "+flightId);
    }

    @Transactional
    public FlightResponse updateFlightStatusAsCancelled(int flightId, Airline airline, CancelFlightRequest request) throws EntityNotFoundException {
        int isQuerySuccess = flightRepositories.updateFlightStatusByIdAndAirline(FlightStatus.CANCELLED, flightId, airline.getId());
        Optional<Flight> optionalFlight = flightRepositories.findById(flightId);
        if(isQuerySuccess == 1  && optionalFlight.isPresent()){
            AirlineResponse airlineResponse = new AirlineResponse();
            BeanUtils.copyProperties(optionalFlight.get().getAirline(), airlineResponse);

            FlightResponse flightResponse = new FlightResponse();
            BeanUtils.copyProperties(optionalFlight.get(), flightResponse);

            flightResponse.setAirline(airlineResponse);
            notifyFlightStatusUpdate(
                    optionalFlight.get().getId(),
                    indigo.hacktohire.commons.DTOs.FlightStatus.CANCELLED,
                    Time.from(optionalFlight.get().getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString(),
                    request.getMessage()
            );
            return flightResponse;
        }
        throw new EntityNotFoundException("Oops!! Cannot cancel flight "+flightId);
    }

    @Transactional
    public FlightResponse updateFlightGate(int flightId, Airline airline, UpdateFlightGateRequest request) throws EntityNotFoundException {
        int isQuerySuccess = flightRepositories.updateFlightGate(request.getDepartureGate(), FlightStatus.GATE_CHANGED, flightId, airline.getId());
        Optional<Flight> optionalFlight = flightRepositories.findById(flightId);
        if(isQuerySuccess == 1  && optionalFlight.isPresent()){
            AirlineResponse airlineResponse = new AirlineResponse();
            BeanUtils.copyProperties(optionalFlight.get().getAirline(), airlineResponse);

            FlightResponse flightResponse = new FlightResponse();
            BeanUtils.copyProperties(optionalFlight.get(), flightResponse);

            flightResponse.setAirline(airlineResponse);
            notifyFlightStatusUpdate(
                    optionalFlight.get().getId(),
                    FlightStatus.GATE_CHANGED,
                    Time.from(optionalFlight.get().getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString(),
                    request.getMessage()
            );
            return flightResponse;
        }
        throw new EntityNotFoundException("Oops!! Cannot update flight gate for flight "+flightId);
    }


    private void notifyFlightStatusUpdate(int flightId, FlightStatus flightStatus, String flightStatusUpdatedTo, String customMessage){
        List<Booking> bookingList = bookingService.getAllPassengersByFlight(flightId);
        List<Flight> flightList = flightRepositories.findAllByDepartureDateTimeBetween(LocalDateTime.now(), LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        kafkaEmailProducer.produce(utility.convertBookingListToPassengersEmailObjectList(bookingList, flightStatus, flightStatusUpdatedTo, customMessage));
        kafkaWhatsappProducer.produce(utility.convertBookingListToPassengersWhatsappObjectList(bookingList, flightStatus, flightStatusUpdatedTo, customMessage));
        kafkaFlightDepartureStatusProducer.produce(utility.convertFlightListToFlightStatusObjectList(flightList));

        System.out.println("CUSTOM Message: "+customMessage);

    }

}