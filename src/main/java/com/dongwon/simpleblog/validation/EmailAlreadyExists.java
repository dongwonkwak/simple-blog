package com.dongwon.simpleblog.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailAlreadyExists {
    String message() default "{EmailAlreadyExists}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
