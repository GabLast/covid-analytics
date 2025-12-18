package com.myorg.covid_analytics.controller;

import com.myorg.covid_analytics.dto.response.GenericResponse;
import com.myorg.covid_analytics.exceptions.InvalidDataFormat;
import com.myorg.covid_analytics.exceptions.ResourceExistsException;
import com.myorg.covid_analytics.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestApiAdvise {

    @ExceptionHandler(
            exception = ResourceNotFoundException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected GenericResponse badRequest(ResourceNotFoundException ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = ResourceExistsException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CONFLICT)
    protected GenericResponse badRequest(ResourceExistsException ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = InvalidDataFormat.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(InvalidDataFormat ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = AccessDeniedException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    protected GenericResponse badRequest(AccessDeniedException ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = NoSuchElementException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected GenericResponse badRequest(NoSuchElementException ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = DateTimeParseException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected GenericResponse badRequest(DateTimeParseException ex, WebRequest request) {
        return GenericResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .build();
    }
}
