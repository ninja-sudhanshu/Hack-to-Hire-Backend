package indigo.hacktohire.email_smtp_client.services;

import indigo.hacktohire.commons.DTOs.EmailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEmailConsumer {

    @Autowired
    EmailSender emailSender;

    @KafkaListener(topics = "emails")
    public void consume(EmailObject emailObject){
        emailSender.send(emailObject);
    }

}