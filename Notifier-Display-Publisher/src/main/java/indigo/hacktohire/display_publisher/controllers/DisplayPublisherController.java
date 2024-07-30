package indigo.hacktohire.display_publisher.controllers;

import indigo.hacktohire.display_publisher.services.DisplayPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DisplayPublisherController {

    @Autowired
    DisplayPublisher displayPublisher;

    @MessageMapping("/departures")
    public void helloDeparture() throws Exception {
        Thread.sleep(1000);
        displayPublisher.publish();
    }

}
