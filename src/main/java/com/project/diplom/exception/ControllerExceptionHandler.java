package com.project.diplom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ExceptionHandler(DiplomaException.class)
    public ResponseEntity<ExceptionInfo> handleDiplomaException(DiplomaException ex) {
        return new ResponseEntity<>(new ExceptionInfo(ex.getMessage(), ex.getStatus().getCode(), new Date()),
                HttpStatus.valueOf(ex.getStatus().getCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ExceptionInfo> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StringBuilder messageBuilder = new StringBuilder("Validation failure. Those parameters has erros: ");
        constraintViolations
                .forEach(constraintViolation ->
                        messageBuilder.append(String.format("%s has value '%s'. %s", constraintViolation.getPropertyPath(),
                                constraintViolation.getInvalidValue(), constraintViolation.getMessage())));

        return new ResponseEntity<>(new ExceptionInfo(messageBuilder.toString(), HttpStatus.BAD_REQUEST.value(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
