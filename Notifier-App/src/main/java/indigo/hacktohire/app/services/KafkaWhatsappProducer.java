package indigo.hacktohire.app.services;

import indigo.hacktohire.commons.DTOs.WhatsappObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaWhatsappProducer {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(WhatsappObject whatsappObject){
        kafkaTemplate.send("whatsapp_messages", whatsappObject);
    }

    public void produce(List<WhatsappObject> whatsappObjectList){
        for(WhatsappObject whatsappObject : whatsappObjectList){
            kafkaTemplate.send("whatsapp_messages", whatsappObject);
        }
    }

}
