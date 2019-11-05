package com.test.banshee.exception.notfound;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException{

    private HttpStatus status;

    public EntityNotFoundException(final String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
