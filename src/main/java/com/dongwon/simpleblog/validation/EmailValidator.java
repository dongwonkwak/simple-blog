package com.dongwon.simpleblog.validation;

import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public record EmailValidator(UserService userService) implements ConstraintValidator<EmailAlreadyExists, UserDto> {

    @Override
    public void initialize(EmailAlreadyExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasText(userDto.email()) && userService.existsByEmail(userDto.email())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{EmailAlreadyExists}")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
