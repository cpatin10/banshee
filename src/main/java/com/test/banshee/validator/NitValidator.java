package com.test.banshee.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NitValidator implements ConstraintValidator<Nit, String> {

    @Override
    public boolean isValid(final String nit, final ConstraintValidatorContext constraintValidatorContext) {
        return nit != null && nit.matches("[0-9]+") && (nit.length() <= 15);
    }

}
