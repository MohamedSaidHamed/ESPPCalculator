package com.taxcalculator.espp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidRateException extends RuntimeException{
    public InvalidRateException(String message) {
        super(message);
    }
}
