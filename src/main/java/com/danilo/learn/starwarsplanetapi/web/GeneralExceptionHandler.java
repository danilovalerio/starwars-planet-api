package com.danilo.learn.starwarsplanetapi.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * Quando ocorrer essa exceção ela será tratada e lançada um Status Conflict
     * @param ex Exceção que será tratada
     * @return Status de Conflict
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    private ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }




}
