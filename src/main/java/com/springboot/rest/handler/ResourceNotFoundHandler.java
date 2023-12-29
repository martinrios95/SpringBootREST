package com.springboot.rest.handler;

import com.springboot.rest.response.ServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ResourceNotFoundHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ServiceResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setCode(HttpStatus.NOT_FOUND.value());
        serviceResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
    }
}
