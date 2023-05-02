// Création d'un validateur personnalisé qui applique les règles suivantes : première lettre en majuscule, 
// pas de caractères spéciaux et de chiffres et espaces autorisés. 

package com.back.projet3.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    // La méthode "isValid(String name, ConstraintValidatorContext context)" est appelée pour valider la 
    // contrainte personnalisée. Elle prend en paramètre le nom à valider et un contexte de validation. 
    // Elle retourne un booléen qui indique si le nom est valide ou non.

    public boolean isValid(String name, ConstraintValidatorContext context) {
        
        // Vérifie si le nom est null. Si c'est le cas, retourne true car la validation réussit automatiquement.

        if (name == null) {
            return true;
        }

        // Vérifie si la première lettre est en majuscule. Si ce n'est pas le cas, retourne false car le nom 
        // doit commencer par une lettre majuscule.

        if (!Character.isUpperCase(name.charAt(0))) {
            return false;
        }

        // Vérifie s'il y a des caractères spéciaux ou des chiffres. Si ce n'est pas le cas, retourne false 
        // car le nom ne doit pas contenir de chiffres ou de caractères spéciaux.

        if (!Pattern.matches("[a-zA-Z\\s]+", name)) {
            return false;
        }

        // La méthode renvoie true si toutes les étapes de validation réussissent, ce qui signifie que le nom 
        // est valide. Sinon, elle renvoie false. 

        return true;
    }
    
} 
