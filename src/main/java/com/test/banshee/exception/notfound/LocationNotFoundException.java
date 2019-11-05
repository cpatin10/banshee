package com.test.banshee.exception.notfound;

public class LocationNotFoundException extends EntityNotFoundException {

    public LocationNotFoundException(String city, String state, String country) {
        super(String.format("The location %s, %s, %s is not supported", city, state, country));
    }

}
