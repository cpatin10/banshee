package com.test.banshee.exception.notfound;

public class VisitNotFoundException extends EntityNotFoundException {

    public VisitNotFoundException(long visitId) {
        super(String.format("The visit with id %s does not exists", visitId));
    }

}
