package com.grandpaweather.weatherservice.helper;

import com.grandpaweather.weatherservice.domain.enums.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
@RequiredArgsConstructor
public class RequestTypeValidator implements ConstraintValidator<RequestTypeConstraint, String> {



    @Override
    public void initialize(RequestTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String requestType, ConstraintValidatorContext context) {
        RequestType.valueOf(requestType.toUpperCase());
        int a=1;
        return false;
    }
}
