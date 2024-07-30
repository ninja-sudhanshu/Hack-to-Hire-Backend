package indigo.hacktohire.app.controllers;

import indigo.hacktohire.app.services.KafkaEmailProducer;
import indigo.hacktohire.app.services.KafkaWhatsappProducer;
import indigo.hacktohire.commons.DTOs.EmailObject;
import indigo.hacktohire.commons.DTOs.FlightStatus;
import indigo.hacktohire.commons.DTOs.WhatsappObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    KafkaEmailProducer kafkaEmailProducer;

    @Autowired
    KafkaWhatsappProducer kafkaWhatsappProducer;

    @GetMapping(path = "")
    public String hmm(){
        return "Working fine if you can access only with JWT";
    }

    @GetMapping(path = "/email/rescheduled")
    public String emailRescheduled(){
        EmailObject emailObject = new EmailObject();
        emailObject.setFlightNumber("6E-444");
        emailObject.setFlightRoute("from Indore to Delhi");
        emailObject.setFlightDate("28-July-2024");
        emailObject.setRecipientName("Sudhanshu Jha");
        emailObject.setRecipientEmail("sudhanshu28jha@gmail.com");
        emailObject.setPnrNumber("1000001231");
        emailObject.setFlightStatus(FlightStatus.RESCHEDULED);
        emailObject.setFlightStatusUpdatedTo("10 PM IST");
        emailObject.setCustomMessage("Please be advised that may be subject to change.");
        kafkaEmailProducer.produce(emailObject);
        return "OK";
    }

    @GetMapping(path = "/email/canceled")
    public String emailCanceled(){
        EmailObject emailObject = new EmailObject();
        emailObject.setFlightNumber("6E-444");
        emailObject.setFlightRoute("from Indore to Delhi");
        emailObject.setFlightDate("28-July-2024");
        emailObject.setRecipientName("Sudhanshu Jha");
        emailObject.setRecipientEmail("sudhanshu28jha@gmail.com");
        emailObject.setPnrNumber("1000001231");
        emailObject.setFlightStatus(FlightStatus.CANCELLED);
        emailObject.setFlightStatusUpdatedTo("");
        emailObject.setCustomMessage("We understand the inconvenience caused to you.<br>As a token to care we would like to offer your $100 Discount on your next flight with us.");
        kafkaEmailProducer.produce(emailObject);
        return "OK";
    }

    @GetMapping(path = "/email/gate-changed")
    public String emailGateChanged(){
        EmailObject emailObject = new EmailObject();
        emailObject.setFlightNumber("6E-444");
        emailObject.setFlightRoute("from Indore to Delhi");
        emailObject.setFlightDate("28-July-2024");
        emailObject.setRecipientName("Sudhanshu Jha");
        emailObject.setRecipientEmail("sudhanshu28jha@gmail.com");
        emailObject.setPnrNumber("1000001231");
        emailObject.setFlightStatus(FlightStatus.GATE_CHANGED);
        emailObject.setFlightStatusUpdatedTo("24B");
        emailObject.setCustomMessage("");
        kafkaEmailProducer.produce(emailObject);
        return "OK";
    }

    @GetMapping(path = "/whatsapp/rescheduled")
    public String whatsappRescheduled(){
        WhatsappObject whatsappObject = new WhatsappObject();
        whatsappObject.setFlightNumber("6E-999");
        whatsappObject.setFlightRoute("from Indore to Pune");
        whatsappObject.setFlightDate("29-July-2024");
        whatsappObject.setRecipientName("Sudhanshu Jha");
        whatsappObject.setRecipientNumber("+919826419934");
        whatsappObject.setPnrNumber("1000XX812391");
        whatsappObject.setFlightStatus(FlightStatus.RESCHEDULED);
        whatsappObject.setFlightStatusUpdatedTo("9PM IST");
        whatsappObject.setCustomMessage("We wish you a happy and safe journey");
        kafkaWhatsappProducer.produce(whatsappObject);
        return "OK";
    }

    @GetMapping(path = "/whatsapp/cancelled")
    public String whatsappCancelled(){
        WhatsappObject whatsappObject = new WhatsappObject();
        whatsappObject.setFlightNumber("6E-999");
        whatsappObject.setFlightRoute("from Indore to Pune");
        whatsappObject.setFlightDate("29-July-2024");
        whatsappObject.setRecipientName("Sudhanshu Jha");
        whatsappObject.setRecipientNumber("+917974407150");
        whatsappObject.setPnrNumber("1000XX812391");
        whatsappObject.setFlightStatus(FlightStatus.CANCELLED);
        whatsappObject.setFlightStatusUpdatedTo("");
        whatsappObject.setCustomMessage("We will provide discount in next trip");
        kafkaWhatsappProducer.produce(whatsappObject);
        return "OK";
    }

    @GetMapping(path = "/whatsapp/gate-changed")
    public String whatsappGateChanged(){
        WhatsappObject whatsappObject = new WhatsappObject();
        whatsappObject.setFlightNumber("6E-999");
        whatsappObject.setFlightRoute("from Indore to Pune");
        whatsappObject.setFlightDate("29-July-2024");
        whatsappObject.setRecipientName("Sudhanshu Jha");
        whatsappObject.setRecipientNumber("+919826419934");
        whatsappObject.setPnrNumber("1000XX812391");
        whatsappObject.setFlightStatus(FlightStatus.GATE_CHANGED);
        whatsappObject.setFlightStatusUpdatedTo("26A");
        whatsappObject.setCustomMessage("Kindly be on time. On-boarding ends in 30 Min");
        kafkaWhatsappProducer.produce(whatsappObject);
        return "OK";
    }

}