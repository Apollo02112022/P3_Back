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


// Le DTO (Data Transfer Object) est un objet qui sert à transférer des données
//  entre différentes couches de l'application.Il simplifie
// et de clarifie la communication entre les différentes couches de l'application
// celui-ci AnnouncementDto utiliser et importer dans announcement controller 

@Data //@Data génère automatiquement des méthodes ici getters et setters
public class AnnouncementDto {
    @Id // clé primaire 
    @GeneratedValue(strategy = GenerationType.IDENTITY)// genere automatiquement l'id
    private Long id;
    @NotBlank
    @Size(max=250)
    private String description;
    
    @Lob //indique que la propriété "announcement_picture" est de type BLOB (Binary Large Object)
    private MultipartFile announcement_picture; //image associer à l'annonce de type MultipartFile


    private User user;
}
