package indigo.hacktohire.app.exceptionHandlers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.dtos.apiresponses.APIResponseError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class DatabaseExceptionHandlers {

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public APIResponse<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        APIResponseError apiResponseError = new APIResponseError();
        apiResponseError.setCode(1);
        apiResponseError.setMessage("Entity Already exists");

        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(Arrays.asList(apiResponseError));
        return apiResponse;
    }

}