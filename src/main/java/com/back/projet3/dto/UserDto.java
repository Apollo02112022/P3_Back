package com.back.projet3.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.back.projet3.validator.ValidName;
import com.back.projet3.validator.ValidPassword;

import lombok.Data;

@Data
public class UserDto {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    
   // @NotBlank : La chaîne de caractère ne doit pas être vide. Il doit y avoir au moins un caractère 
   // et celui-ci ne doit pas crrespondre à un espace. 

    @NotBlank

    // @Size: La chaîne de caractère doit correspondre à la longueur indiquée. 

    @Size(max=20)

    // @ValidName : Annotation permettant d'utiliser le validateur personnalisé NameValidator. 

    @ValidName
    private String lastname;
    
    @NotBlank
    @Size(max=20)
    @ValidName
    private String firstname;
    
    @NotBlank
    @Size(max=10)

    // @Pattern: La chaîne de caractère doit correspondre à une certaine expression régulière.  

    @Pattern(regexp = "\\w+")
    private String pseudo;
    
    // @Lob est une annotation utilisée pour indiquer qu'un champ de l'entité correspondante doit
    //  être stocké sous forme de grand objet binaire (BLOB). Le mot "LOB" signifie "Large Object",
    // il est donc utilisé pour les types de données qui sont trop grands pour être stockés dans 
    // un type de données SQL standard comme VARCHAR ou INTEGER.

    @Lob

    // MultipartFile est un type de données spécifique à Spring. Cela signifie que le champ "picture" 
    // peut contenir un fichier téléchargé via une requête HTTP multipart. En d'autres termes, il permet de 
    // récupérer un fichier que l'utilisateur a envoyé à partir d'un formulaire de téléchargement. 

    private MultipartFile picture;
   
    // @Email: La chaîne de caractère doit correspondre à une adresse mail valide. 

    @Email
    private String mail;
    
    @NotBlank
    @Size(max=20)
    @ValidName
    private String city;

    // @Min: Pour fixer la valeur minimale du champs numérique.

    @Min(971)

    // @Max: Pour fixer la valeur maximale du champs numérique.

    @Max(95999)
    private int county;
   
    @NotBlank
    @Size(min=8)

    // @ValidPassword : Annotation permettant d'utiliser le validateur personnalisé PasswordValidator. 

    @ValidPassword
    private String password;

}
