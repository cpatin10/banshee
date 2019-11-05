package com.test.banshee.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(final String phoneNumber, final ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber != null && phoneNumber.matches("[0-9]+")
                && (phoneNumber.length() >= 7) && (phoneNumber.length() <= 12);
    }

}
