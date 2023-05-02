package com.back.projet3.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    // @ManyToMany(mappedBy = "notifications")
    // private List<User> user_answers = new ArrayList<>();
     // => On liste les utilisateurs qui répondent à des annonces.

    //  C'est pour éviter une boucle infinie d'hibernate lorsqu'on récupère une notification
     @JsonBackReference(value ="user_notification")
    // Données chargées que lorsque l'entité sera utilisée 
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="user_id", referencedColumnName = "id")
     private Notification userNotification;
     // => on joint la colonne user à notification.

}