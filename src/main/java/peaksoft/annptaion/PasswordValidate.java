package peaksoft.annptaion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.exception.BadRequestException;

/**
 * name : kutman
 **/
public class PasswordValidate implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length()>=4) {
            return s.length() > 4;
        }else {
            throw new BadRequestException("password exception");
        }
    }
}
