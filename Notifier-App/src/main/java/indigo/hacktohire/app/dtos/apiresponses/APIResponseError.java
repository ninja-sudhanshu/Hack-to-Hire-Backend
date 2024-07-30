package indigo.hacktohire.app.dtos.apiresponses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class APIResponseError {
    int code;
    String message;
}
