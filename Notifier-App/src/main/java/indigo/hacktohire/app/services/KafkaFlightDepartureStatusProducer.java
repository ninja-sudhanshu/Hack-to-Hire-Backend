package indigo.hacktohire.app.services;

import indigo.hacktohire.commons.DTOs.FlightStatusObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaFlightDepartureStatusProducer {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(List<FlightStatusObject> flightStatusObjectList){
        kafkaTemplate.send("flight_departure_status", flightStatusObjectList);
    }

}