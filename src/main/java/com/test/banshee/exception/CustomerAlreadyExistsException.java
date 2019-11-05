package com.test.banshee.exception;

public class CustomerAlreadyExistsException extends EntityAlreadyExistsException {

    public CustomerAlreadyExistsException(final String nit) {
        super(String.format("Customer with nit %s already exists", nit));
    }

}
