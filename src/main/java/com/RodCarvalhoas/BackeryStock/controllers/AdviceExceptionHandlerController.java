package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.exceptions.StandardError;
import com.RodCarvalhoas.BackeryStock.exceptions.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class AdviceExceptionHandlerController {


    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StandardError handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest webRequest){
        return new StandardError(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                webRequest.getDescription(false));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest webRequest){
        return new StandardError(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                webRequest.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest){
        ValidationError error = new ValidationError(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na validação dos campos",
                webRequest.getDescription(false));

        for(FieldError x : ex.getBindingResult().getFieldErrors()){
            error.addErrors(x.getField(), x.getDefaultMessage());
        }

        return error;
    }



}
