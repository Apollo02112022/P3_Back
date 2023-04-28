package com.back.projet3.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }

        // Vérifie si la première lettre est en majuscule
        if (!Character.isUpperCase(name.charAt(0))) {
            return false;
        }

        // Vérifie s'il y a des caractères spéciaux ou des chiffres
        if (!Pattern.matches("[a-zA-Z\\s]+", name)) {
            return false;
        }

        return true;
    }
    
} 
