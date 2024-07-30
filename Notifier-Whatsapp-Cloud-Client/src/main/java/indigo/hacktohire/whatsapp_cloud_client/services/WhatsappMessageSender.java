package indigo.hacktohire.whatsapp_cloud_client.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import indigo.hacktohire.commons.DTOs.WhatsappObject;
import indigo.hacktohire.whatsapp_cloud_client.configurations.ApplicationProperties;
import indigo.hacktohire.whatsapp_cloud_client.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WhatsappMessageSender {

    @Autowired
    ApplicationProperties applicationProperties;

    void send(WhatsappObject whatsappObject){

        WhatsappMessage whatsappMessage = new WhatsappMessage();
        whatsappMessage.setMessaging_product("whatsapp");
        whatsappMessage.setRecipient_type("individual");
        whatsappMessage.setTo(whatsappObject.getRecipientNumber());
        whatsappMessage.setType("template");

        WhatsappMessageTemplate whatsappMessageTemplate = new WhatsappMessageTemplate();
        whatsappMessageTemplate.setLanguage(new WhatsappMessageLanguage("en"));

        WhatsappMessageComponent whatsappMessageComponent = new WhatsappMessageComponent();
        whatsappMessageComponent.setType("body");

        List<WhatsappMessageParameter> whatsappMessageParameters = new ArrayList<>();
        whatsappMessageParameters.add(new WhatsappMessageParameter("text",whatsappObject.getRecipientName()));
        whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightNumber()));
        whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightRoute()));
        whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getPnrNumber()));

        switch (whatsappObject.getFlightStatus()){
            case RESCHEDULED -> {
                whatsappMessageTemplate.setName("flight_rescheduled");
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightDate()));
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightStatusUpdatedTo()));
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getCustomMessage()));
            }
            case CANCELLED -> {
                whatsappMessageTemplate.setName("flight_cancelled");
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightDate()));
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getCustomMessage()));
            }
            case GATE_CHANGED -> {
                whatsappMessageTemplate.setName("gate_changed");
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getFlightStatusUpdatedTo()));
                whatsappMessageParameters.add(new WhatsappMessageParameter("text", whatsappObject.getCustomMessage()));
            }
        }
        whatsappMessageComponent.setParameters(whatsappMessageParameters);

        whatsappMessageTemplate.setComponents(Arrays.asList(whatsappMessageComponent));
        whatsappMessage.setTemplate(whatsappMessageTemplate);

        try{
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(applicationProperties.getWhatsappBaseURL()))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(whatsappMessage)))
                    .setHeader("Authorization", "Bearer "+applicationProperties.getWhatsappAccessToken())
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpClient.newBuilder()
                    .build()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }catch (Exception exception){
            System.out.println(exception);
        }

    }

}