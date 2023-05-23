package com.back.projet3.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class MailDto {
 // @Email: La chaîne de caractère doit correspondre à une adresse mail valide. 
    @Email
    private String mail;

}
