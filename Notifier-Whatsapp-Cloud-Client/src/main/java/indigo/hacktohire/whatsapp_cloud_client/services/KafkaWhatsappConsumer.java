package indigo.hacktohire.whatsapp_cloud_client.services;

import indigo.hacktohire.commons.DTOs.WhatsappObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaWhatsappConsumer {

    @Autowired
    WhatsappMessageSender whatsappMessageSender;

    @KafkaListener(topics = "whatsapp_messages")
    public void consume(WhatsappObject whatsappObject){
        whatsappMessageSender.send(whatsappObject);
    }

}
