package io.github.wkktoria.game_reviews.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorObject> handleGameNotFoundException(GameNotFoundException exception,
            WebRequest request) {
        return handleException(exception, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorObject> handleReviewNotFoundException(ReviewNotFoundException exception,
            WebRequest request) {
        return handleException(exception, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorObject> handleException(Exception exception, WebRequest request,
            HttpStatusCode statusCode) {
        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(statusCode.value())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorObject, statusCode);
    }
}
