package indigo.hacktohire.app.exceptionHandlers;

import indigo.hacktohire.app.constants.APIResponseStatus;
import indigo.hacktohire.app.dtos.apiresponses.APIResponse;
import indigo.hacktohire.app.dtos.apiresponses.APIResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        APIResponseError apiResponseError = new APIResponseError();
        apiResponseError.setCode(1);
        apiResponseError.setMessage(exception.getMessage());

        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(Arrays.asList(apiResponseError));
        return apiResponse;
    }


    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResponse<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        APIResponseError apiResponseError = new APIResponseError();
        apiResponseError.setCode(2);
        apiResponseError.setMessage("Please make a request with valid request body.");

        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(Arrays.asList(apiResponseError));
        return apiResponse;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<APIResponseError> errorList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            APIResponseError apiResponseError = new APIResponseError();
            apiResponseError.setCode(3);
            apiResponseError.setMessage(error.getDefaultMessage());
            errorList.add(apiResponseError);
        });

        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(errorList);
        return apiResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public APIResponse<Object> handleIllegalArgumentException(IllegalArgumentException exception){

        APIResponseError apiResponseError = new APIResponseError();
        apiResponseError.setCode(5);
        apiResponseError.setMessage(exception.getMessage());
        APIResponse<Object> apiResponse = new APIResponse<>();
        apiResponse.setStatus(APIResponseStatus.FAILED);
        apiResponse.setErrors(List.of(apiResponseError));
        return apiResponse;
    }

}
