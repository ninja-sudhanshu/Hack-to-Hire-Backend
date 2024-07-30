package indigo.hacktohire.whatsapp_cloud_client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WhatsappMessageComponent {
    String type;
    List<WhatsappMessageParameter> parameters;
}