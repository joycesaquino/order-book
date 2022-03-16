package com.meli.orderbook.controller;

import com.meli.orderbook.model.ApiErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        String message = exception.getParameterName() + " " + exception.getMessage();
        ResponseEntity<Object> responseEntity = createResponseEntity(UNPROCESSABLE_ENTITY, message);
        log.debug(Objects.requireNonNull(responseEntity.getBody()).toString());
        return responseEntity;
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception exception) {
        ResponseEntity<Object> responseEntity = createResponseEntity(INTERNAL_SERVER_ERROR, exception.getMessage());
        log.error(Objects.requireNonNull(responseEntity.getBody()).toString());
        return responseEntity;
    }

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleUnprocessableEntity(HttpClientErrorException.UnprocessableEntity exception) {
        ResponseEntity<Object> responseEntity = createResponseEntity(UNPROCESSABLE_ENTITY, exception.getMessage());
        log.debug(Objects.requireNonNull(responseEntity.getBody()).toString());
        return responseEntity;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = exception.getMessage().replace("save.", "");
        ResponseEntity<Object> responseEntity = createResponseEntity(UNPROCESSABLE_ENTITY, message);
        log.debug(Objects.requireNonNull(responseEntity.getBody()).toString());
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseEntity<Object> responseEntity = createResponseEntity(UNPROCESSABLE_ENTITY,
                formatMessageMethodArgumentNotValidException(ex));
        log.debug(Objects.requireNonNull(responseEntity.getBody()).toString());
        return responseEntity;
    }

    private String formatMessageMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(joining(","));

        String fieldsMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(joining(","));
        return String.format("%s %s", fields, fieldsMessages);
    }

    private ResponseEntity<Object> createResponseEntity(HttpStatus status, String message) {

        ApiErrorMessage errorMessage = ApiErrorMessage.builder()
                .status(status)
                .error(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorMessage, status);
    }
}
