package com.dongwon.simpleblog.validation;


import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public record UsernameValidator(
        UserService userService) implements ConstraintValidator<UsernameAlreadyExits, UserDto> {
    @Override
    public void initialize(UsernameAlreadyExits constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasText(userDto.username()) && userService.existsByUsername(userDto.username())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{UsernameAlreadyExists}")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
