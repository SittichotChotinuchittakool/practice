package com.example.wongnai.exceptions;

import java.time.LocalDateTime;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.wongnai.model.CustomErrorResponse;

//@RestControllerAdvice
public class ConfigExceptionHandler {
    private DefaultErrorAttributes attr = new DefaultErrorAttributes();

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleCustomException(ResourceNotFoundException ex) {
        //must log exception msg or something useful
        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", ex.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
