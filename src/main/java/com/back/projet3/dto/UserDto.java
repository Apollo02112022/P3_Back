package com.back.projet3.dto;

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

    @NotBlank
    @Size(max=20)
    @ValidName
    private String lastname;
    
    @NotBlank
    @Size(max=20)
    @ValidName
    private String firstname;
    
    @NotBlank
    @Size(max=10) 
    @Pattern(regexp = "\\w+")
    private String pseudo;
    
    @Lob
    private MultipartFile picture;

    @Email
    private String mail;
    
    @NotBlank
    @Size(max=20)
    @ValidName
    private String city;

    @Min(971)
    @Max(95999)
    private int county;
   
    @NotBlank
    @Size(min=8)

    @ValidPassword
    private String password;

}
