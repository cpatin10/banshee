package com.test.banshee.exception.notfound;

public class CustomerNotFoundException extends EntityNotFoundException {

    public CustomerNotFoundException(long customerId) {
        super(String.format("The customer with id %s does not exists", customerId));
    }

}
