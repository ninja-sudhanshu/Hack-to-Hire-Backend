package indigo.hacktohire.display_publisher.services;

import indigo.hacktohire.commons.DTOs.FlightStatusObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaFlightStatusConsumer {

    @Autowired
    DisplayPublisher displayPublisher;

    @KafkaListener(topics = "flight_departure_status")
    public void consume(List<FlightStatusObject> flightStatusObjectList){
        displayPublisher.publish(flightStatusObjectList);
    }

}