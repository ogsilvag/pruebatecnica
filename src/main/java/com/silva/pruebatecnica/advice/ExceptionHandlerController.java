package com.silva.pruebatecnica.advice;

import com.silva.pruebatecnica.models.ErrorDto;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException ex) {

    ErrorDto dto = new ErrorDto(HttpStatus.BAD_REQUEST, "Validation error");

    dto.setDetailedMessages(ex.getBindingResult().getAllErrors().stream()
        .map(err -> err.unwrap(ConstraintViolation.class))
        .map(err -> String.format("'%s' %s", err.getPropertyPath(), err.getMessage()))
        .toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);

  }
}
