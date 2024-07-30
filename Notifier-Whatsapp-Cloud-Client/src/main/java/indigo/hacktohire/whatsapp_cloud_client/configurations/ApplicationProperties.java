package indigo.hacktohire.whatsapp_cloud_client.configurations;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties
public class ApplicationProperties {
    @Value("${whatsapp.access-token}")
    public String whatsappAccessToken;

    @Value("${whatsapp.base-url}")
    public String whatsappBaseURL;
}
