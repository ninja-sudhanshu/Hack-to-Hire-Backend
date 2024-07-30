package indigo.hacktohire.display_publisher.services;

import indigo.hacktohire.commons.DTOs.FlightStatusObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class DisplayPublisher {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    static List<FlightStatusObject> currentFlightStatusObjectList = new ArrayList<>();

    public void publish(){
        simpMessagingTemplate.convertAndSend("/topic/departures", currentFlightStatusObjectList);
    }

    public void publish(List<FlightStatusObject> flightStatusObjectList){
        currentFlightStatusObjectList = flightStatusObjectList;
        simpMessagingTemplate.convertAndSend("/topic/departures", flightStatusObjectList);
    }

}