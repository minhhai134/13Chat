package SD.ChatApp.exception.handler;

import SD.ChatApp.exception.friend.FriendRelationshipExistedException;
import SD.ChatApp.exception.friend.FriendRelationshipNotFound;
import SD.ChatApp.exception.friend.FriendRequestExistedException;
import SD.ChatApp.exception.request.InvalidRequestException;
import SD.ChatApp.exception.user.NameExistedException;
import SD.ChatApp.exception.user.UserNameExistedException;
import SD.ChatApp.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice()
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_CODE_INTERNAL = "INTERNAL_ERROR";

    private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS_CODE = Map.of(
            BadCredentialsException.class, HttpStatus.FORBIDDEN,
            UserNotFoundException.class, HttpStatus.NOT_FOUND,
            UserNameExistedException.class, HttpStatus.CONFLICT,
            FriendRelationshipExistedException.class, HttpStatus.CONFLICT,
            FriendRequestExistedException.class, HttpStatus.CONFLICT,
            InvalidRequestException.class, HttpStatus.BAD_REQUEST,
            FriendRelationshipNotFound.class, HttpStatus.CONFLICT,
            NameExistedException.class, HttpStatus.CONFLICT,
            DataAccessException.class, HttpStatus.INTERNAL_SERVER_ERROR
    );


    private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE = Map.of(
            BadCredentialsException.class, "BAD_CREDENTIALS",
            UserNotFoundException.class, "USER_NOT_FOUND",
            UserNameExistedException.class, "USER_NAME_EXISTED",
            FriendRelationshipExistedException.class, "FRIEND_RELATIONSHIP_EXISTED",
            FriendRequestExistedException.class, "FRIEND_REQUEST_EXISTED",
            InvalidRequestException.class, "INVALID_REQUEST_BODY",
            FriendRelationshipNotFound.class, "FRIEND_RELATIONSHIP_NOT_FOUND",
            NameExistedException.class, "NAME_EXISTED",
            DataAccessException.class, "DATABASE_CONFLICT"
    );

    @ExceptionHandler()
    ResponseEntity<ApiExceptionResponse> handleException(RuntimeException exception){
        HttpStatus status = EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(exception.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR);

        String errorCode = EXCEPTION_TO_ERROR_CODE.getOrDefault(exception.getClass(),
                ERROR_CODE_INTERNAL);
//        log.info("Class: {}, Code: {}", exception.getClass(), errorCode);

        final ApiExceptionResponse response = ApiExceptionResponse.builder().status(status).
                errorCode(errorCode).build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
