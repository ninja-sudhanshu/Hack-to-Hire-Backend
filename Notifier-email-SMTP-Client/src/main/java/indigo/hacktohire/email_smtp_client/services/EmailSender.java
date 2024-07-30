package indigo.hacktohire.email_smtp_client.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import indigo.hacktohire.commons.DTOs.EmailObject;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailSender {

    @Autowired
    Configuration configuration;

    @Autowired
    JavaMailSender javaMailSender;

    void send(EmailObject emailObject){

        String emailTemplateName = "";
        String emailSubjectLine = "";
        switch (emailObject.getFlightStatus()){
            case RESCHEDULED -> {
                emailTemplateName = "rescheduled-template.flt";
                emailSubjectLine = "RESCHEDULED: Your Flight "+ emailObject.getFlightNumber()+" "+ emailObject.getFlightRoute()+" has been Rescheduled.";
            }
            case CANCELLED -> {
                emailTemplateName = "cancelled-template.flt";
                emailSubjectLine = "CANCELLED: Your Flight "+ emailObject.getFlightNumber()+" "+ emailObject.getFlightRoute()+" has been Cancelled.";
            }
            case GATE_CHANGED -> {
                emailTemplateName = "gate-changed-template.flt";
                emailSubjectLine = "GATE CHANGED: Gate for your Flight "+ emailObject.getFlightNumber()+" "+ emailObject.getFlightRoute()+" has changed.";
            }
        }

        try {
            configuration.setNumberFormat("computer");

            Map<String, Object> emailProperties = new HashMap<>();
            emailProperties.put("pnrNumber", emailObject.getPnrNumber());
            emailProperties.put("name", emailObject.getRecipientName());
            emailProperties.put("flightNumber", emailObject.getFlightNumber());
            emailProperties.put("flightRoute", emailObject.getFlightRoute());
            emailProperties.put("flightDate", emailObject.getFlightDate());
            emailProperties.put("flightStatusUpdatedTo", emailObject.getFlightStatusUpdatedTo());
            emailProperties.put("customMessage", emailObject.getCustomMessage());

            Template emailTemplate = configuration.getTemplate(emailTemplateName);
            String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, emailProperties);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            mimeMessageHelper.setTo(Objects.requireNonNull(emailObject.getRecipientEmail()));
            mimeMessageHelper.setSubject(emailSubjectLine);
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setFrom("hello@waoo.in","Waoo Flights");

            javaMailSender.send(mimeMessage);

        }catch (Exception exception){
            System.err.println("Exception: " + exception.getMessage());
        }
    }
}