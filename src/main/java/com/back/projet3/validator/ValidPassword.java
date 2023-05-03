package com.back.projet3.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PasswordValidator.class })
public @interface ValidPassword {

    String message() default "Mot de passe invalide.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

} 
