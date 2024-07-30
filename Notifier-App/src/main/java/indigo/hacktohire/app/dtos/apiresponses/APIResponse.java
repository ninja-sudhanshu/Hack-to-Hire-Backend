package indigo.hacktohire.app.dtos.apiresponses;

import indigo.hacktohire.app.constants.APIResponseStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class APIResponse<T> {
    APIResponseStatus status;
    List<APIResponseError> errors = new ArrayList<>();
    T data;
}