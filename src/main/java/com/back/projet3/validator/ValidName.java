// Création de l'annotation @ValidName afin de pouvoir utiliser le validateur personnalisé NameValidator. 

package com.back.projet3.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

// @Target({FIELD}) est une annotation qui indique que cette annotation ne peut être utilisée que pour annoter 
// des champs.

@Target({FIELD})

// @Retention(RUNTIME) est une annotation qui indique que l'annotation doit être conservée au moment de l'exécution.

@Retention(RUNTIME)

// @Documented indique que cette annotation doit être incluse dans la documentation générée.

@Documented

// @Constraint(validatedBy = NameValidator.class) est une annotation qui spécifie que cette annotation est une 
// contrainte de validation personnalisée et que la classe "NameValidator" sera utilisée pour valider le champ annoté.

@Constraint(validatedBy = NameValidator.class) 

// public @interface ValidName est une déclaration d'interface qui définit l'annotation "ValidName". 
// C'est une annotation personnalisée utilisée pour valider le nom, le prénom ou la ville d'un utilisateur.

public @interface ValidName {

    // "message()" est une méthode qui retourne une chaîne de caractères décrivant le message d'erreur 
    // à afficher en cas de validation échouée.

    String message() default "Chaîne de caractère invalide.";

    // "groups()" est une méthode qui retourne une liste de groupes de validation auxquels cette contrainte 
    // de validation appartient. Les groupes peuvent être utilisés pour appliquer des contraintes de validation 
    // spécifiques à des groupes de champs.

    Class<?>[] groups() default {}; 

    // "payload()" est une méthode qui retourne une liste de charges utiles associées à cette contrainte de validation. 
    // Les charges utiles peuvent être utilisées pour spécifier des informations supplémentaires à transmettre lors de 
    // la validation.

    Class<? extends Payload>[] payload() default {};
    
} 
