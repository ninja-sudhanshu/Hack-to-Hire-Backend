package indigo.hacktohire.whatsapp_cloud_client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WhatsappMessage {
    String messaging_product;
    String recipient_type;
    String to;
    String type;
    WhatsappMessageTemplate template;
}