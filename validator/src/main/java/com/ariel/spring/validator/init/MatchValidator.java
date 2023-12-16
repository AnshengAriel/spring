package com.ariel.spring.validator.init;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MatchValidator implements ConstraintValidator<Match, Object> {

    private Pattern pattern;
    private String msg;

    @Override
    public void initialize(Match constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
        msg = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String s = o == null ? "null" : o.toString();
        if (pattern.matcher(s).matches()) {
            return true;
        }
        throw new IllegalArgumentException(msg);
    }

}
