package br.com.mysafeestablishmentcompany.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> handlerProductNotFoundException(ProductNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handlerGenericException(Exception e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
}
