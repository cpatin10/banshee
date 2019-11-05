package com.test.banshee.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target( {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
