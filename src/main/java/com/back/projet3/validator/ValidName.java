// Création de l'annotation @ValidName afin de pouvoir utiliser le validateur personnalisé NameValidator. 

package com.back.projet3.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
// import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({FIELD})
@Retention(RUNTIME)
// @Documented
@Constraint(validatedBy = NameValidator.class) 
public @interface ValidName {

    String message() default "Chaîne de caractère invalide.";

    Class<?>[] groups() default {}; 

    Class<? extends Payload>[] payload() default {};
    
} 
