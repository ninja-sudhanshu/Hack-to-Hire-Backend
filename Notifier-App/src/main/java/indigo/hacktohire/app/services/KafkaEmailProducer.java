package indigo.hacktohire.app.services;

import indigo.hacktohire.commons.DTOs.EmailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaEmailProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(EmailObject emailObject){
        kafkaTemplate.send("emails", emailObject);
    }

    public void produce(List<EmailObject> emailObjectList){
        for(EmailObject emailObject : emailObjectList){
            kafkaTemplate.send("emails", emailObject);
        }
    }

}