package com.test.banshee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private HttpStatus status;

    public EntityAlreadyExistsException(final String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

}
