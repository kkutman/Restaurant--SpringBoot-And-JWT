package peaksoft.annptaion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * name : kutman
 **/
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Documented
@Constraint(validatedBy = UnicValidate.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unic {
    String message() default "invalid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
