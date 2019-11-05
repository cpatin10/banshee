package com.test.banshee.exception.notfound;

public class SalesRepresentativeNotFoundException extends EntityNotFoundException {

    public SalesRepresentativeNotFoundException(String identificationNumber) {
        super(String.format(
                "The sales representative with identification number %s does not exists", identificationNumber));
    }

}
