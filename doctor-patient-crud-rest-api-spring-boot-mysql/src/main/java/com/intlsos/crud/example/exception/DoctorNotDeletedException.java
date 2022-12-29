package com.intlsos.crud.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class DoctorNotDeletedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 223406952505246000L;
    private Object fieldValue;

    public DoctorNotDeletedException(Object fieldValue) {
        super(String.format("Doctor with doctor id : '%s' cannot be deleted, as it is referenced by patient record(s).", fieldValue));
        this.fieldValue = fieldValue;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
