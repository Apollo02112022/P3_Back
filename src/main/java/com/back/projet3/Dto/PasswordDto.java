package com.back.projet3.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.back.projet3.validator.ValidPassword;

import lombok.Data;

@Data
public class PasswordDto {

    @NotBlank
    @Size(min=8)
    
     // @ValidPassword : Annotation permettant d'utiliser le validateur personnalisé PasswordValidator. 

    @ValidPassword
    private String password;

}
