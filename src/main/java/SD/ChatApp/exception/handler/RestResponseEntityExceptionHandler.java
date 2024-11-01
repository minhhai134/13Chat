package SD.ChatApp.exception.handler;

import SD.ChatApp.exception.UserNameExistedException;
import SD.ChatApp.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice()
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_CODE_INTERNAL = "INTERNAL_ERROR";

    private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS_CODE = Map.of(
            UserNotFoundException.class, HttpStatus.NOT_FOUND,
            UserNameExistedException.class, HttpStatus.CONFLICT
    );


    private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE = Map.of(
            UserNotFoundException.class, "USER_NOT_FOUND",
            UserNameExistedException.class, "USER_NAME_EXISTED"
    );

    @ExceptionHandler()
    ResponseEntity<ApiExceptionResponse> handleException(RuntimeException exception){
        HttpStatus status = EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(exception.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR);

        String errorCode = EXCEPTION_TO_ERROR_CODE.getOrDefault(exception.getClass(),
                ERROR_CODE_INTERNAL);

        final ApiExceptionResponse response = ApiExceptionResponse.builder().status(status).
                errorCode(errorCode).build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
