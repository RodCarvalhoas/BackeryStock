package com.RodCarvalhoas.BackeryStock.controllers;

import com.RodCarvalhoas.BackeryStock.exceptions.DataIntegrityViolationException;
import com.RodCarvalhoas.BackeryStock.exceptions.ObjectNotFoundException;
import com.RodCarvalhoas.BackeryStock.exceptions.StandardError;
import com.RodCarvalhoas.BackeryStock.exceptions.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdviceExceptionHandlerControllerTest {

    @InjectMocks
    private AdviceExceptionHandlerController adviceExceptionHandlerController;

    @Mock
    private WebRequest webRequest;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleObjectNotFoundException() {
        ObjectNotFoundException exception = new ObjectNotFoundException("Object not found");
        when(webRequest.getDescription(false)).thenReturn("Description");

        StandardError result = adviceExceptionHandlerController.handleObjectNotFoundException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        assertEquals("Object not found", result.getError());
        assertEquals("Description", result.getDescription());
    }

    @Test
    public void testHandleDataIntegrityViolationExceptionCustom() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Data integrity violation");
        when(webRequest.getDescription(false)).thenReturn("Description");

        StandardError result = adviceExceptionHandlerController.handleDataIntegrityViolationExceptionCustom(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertEquals("Data integrity violation", result.getError());
        assertEquals("Description", result.getDescription());
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
        when(webRequest.getDescription(false)).thenReturn("Description");

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("object", "field1", "Field1 error message"));
        fieldErrors.add(new FieldError("object", "field2", "Field2 error message"));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        ValidationError result = (ValidationError) adviceExceptionHandlerController.handleMethodArgumentNotValidException(exception, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertEquals("Erro na validação dos campos", result.getError());
        assertEquals("Description", result.getDescription());
        assertEquals(2, result.getErrors().size());
        assertEquals("field1", result.getErrors().get(0).getFieldName());
        assertEquals("Field1 error message", result.getErrors().get(0).getMessage());
        assertEquals("field2", result.getErrors().get(1).getFieldName());
        assertEquals("Field2 error message", result.getErrors().get(1).getMessage());
    }
}
