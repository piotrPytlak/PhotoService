package pl.pytlak.photoart.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, FIELD, ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidatorAvatar.class)
@Documented
public @interface ValidAvatar {
    String message() default "The string entered is not an email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
