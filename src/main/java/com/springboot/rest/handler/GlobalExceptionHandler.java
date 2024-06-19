package com.springboot.rest.handler;

import com.springboot.rest.response.ServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServiceResponse<?>> handleHttpMessageNotReadableException(NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());

        return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ServiceResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse<?>> handleException(Exception ex, HttpServletRequest httpServletRequest) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage().replace("\"", ""));

        return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
    }
}