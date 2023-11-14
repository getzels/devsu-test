package com.pichincha.pichinchaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomAPIException extends RuntimeException {

    public CustomAPIException() {
        super();
    }

    public CustomAPIException(String message) {
        super(message);
    }

    public CustomAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomAPIException(Throwable cause) {
        super(cause);
    }
}

