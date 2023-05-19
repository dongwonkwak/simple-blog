package com.dongwon.simpleblog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.passay.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.*;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSAY_PROPERTIES_FILE = "passay_%s.properties";
    private final MessageResolver resolver = createMessageResolver();
    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (resolver == null) {
            return true;
        }

        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 16),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                // no whitespace
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }


    private MessageResolver createMessageResolver() {
        String lang = LocaleContextHolder.getLocale().getLanguage();

        Properties props = new Properties();
        String propsFile = "passay.properties";

        if (!lang.contains("en")) {
            propsFile = String.format(PASSAY_PROPERTIES_FILE, lang);
        }

        try (var inputStream = getClass().getClassLoader().getResourceAsStream(propsFile)) {
            props.load(inputStream);
        } catch (IOException exception) {
            return null;
        }

        return new PropertiesMessageResolver(props);
    }
}
