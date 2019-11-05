package com.test.banshee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Getter
public class ExceededCreditLimitException extends RuntimeException{

    private HttpStatus status;

    public ExceededCreditLimitException(final BigDecimal visitTotal, final BigDecimal creditLimit) {
        super(String.format(
                "Visit total amount exceeds the customer's credit limit -> Credit limit: $%s vs Visit total: $%s",
                visitTotal, creditLimit));
        status = HttpStatus.BAD_REQUEST;
    }

}
