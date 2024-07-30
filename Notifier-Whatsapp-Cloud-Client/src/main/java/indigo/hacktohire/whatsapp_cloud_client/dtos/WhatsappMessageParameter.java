package indigo.hacktohire.whatsapp_cloud_client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WhatsappMessageParameter {
    String type;
    Object text;
}