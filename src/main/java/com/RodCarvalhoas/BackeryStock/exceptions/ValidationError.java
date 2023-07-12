package com.RodCarvalhoas.BackeryStock.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Date timestamp, Integer status, String error, String description) {
        super(timestamp, status, error, description);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
