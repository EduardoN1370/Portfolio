package com.microservice.receipt.Controller;

import com.microservice.receipt.Dto.ErrorDto;
import com.microservice.receipt.Exceptions.BusinessException;
import com.microservice.receipt.Exceptions.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<ErrorDto> runtimeException(RuntimeException ex){
        ErrorDto error= ErrorDto.builder().code("P-500").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value= RequestException.class)
    public ResponseEntity<ErrorDto> requestExceptionHandler(RequestException ex){
        ErrorDto error= ErrorDto.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value= BusinessException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(BusinessException ex){
        ErrorDto error= ErrorDto.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }


}
