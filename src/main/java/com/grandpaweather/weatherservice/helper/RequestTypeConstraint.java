package com.grandpaweather.weatherservice.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = RequestTypeValidator.class)
public @interface RequestTypeConstraint {

    String message() default "Invalid request type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
