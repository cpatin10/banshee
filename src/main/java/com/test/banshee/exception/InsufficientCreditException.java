package com.test.banshee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Getter
public class InsufficientCreditException extends RuntimeException {

    private HttpStatus status;

    public InsufficientCreditException(final BigDecimal visitTotal, final BigDecimal availableCredit) {
        super(String.format("Customer does not have enough credit -> Current credit: $%s vs Visit total: $%s",
                availableCredit, visitTotal));
        status = HttpStatus.BAD_REQUEST;
    }

}
