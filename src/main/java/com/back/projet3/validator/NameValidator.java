// Création d'un validateur personnalisé qui applique les règles suivantes : première lettre en majuscule, 
// pas de caractères spéciaux et de chiffres et espaces autorisés. 

package com.back.projet3.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    public boolean isValid(String name, ConstraintValidatorContext context) {

        if (name == null) {
            return true;
        }

        if (!Character.isUpperCase(name.charAt(0))) {
            return false;
        }

        if (!Pattern.matches("[a-zA-Z\\s]+", name)) {
            return false;
        }

        return true;
    }
    
} 
