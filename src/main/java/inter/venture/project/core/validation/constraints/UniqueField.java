package inter.venture.project.core.validation.constraints;

import inter.venture.project.core.validation.UniqueFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueFieldValidator.class)
@Documented
public @interface UniqueField {

    String message() default "The field is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<?> entity();

    String fieldName();

}
