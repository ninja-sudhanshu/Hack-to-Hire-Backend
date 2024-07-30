package indigo.hacktohire.app.exceptionHandlers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.dtos.apiresponses.APIResponseError;
import indigo.hacktohire.app.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public APIResponse<Object> handleBankNotFoundException(EntityNotFoundException exception){
        APIResponseError apiResponseError = new APIResponseError();
        apiResponseError.setCode(3);
        apiResponseError.setMessage(exception.getMessage());

        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(Arrays.asList(apiResponseError));
        return apiResponse;
    }

}
