package com.back.projet3.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.back.projet3.entity.User;

import lombok.Data;


@Data
public class AnnouncementDto {
    @Id // clé primaire 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max=250)
    private String description;
    
    @Lob 
    private MultipartFile announcement_picture; 


    private User user;
}
