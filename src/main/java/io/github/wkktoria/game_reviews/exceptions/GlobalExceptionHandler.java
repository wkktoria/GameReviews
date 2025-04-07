package io.github.wkktoria.game_reviews.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorObject> handleGameNotFoundException(GameNotFoundException exception,
            WebRequest request) {
        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
