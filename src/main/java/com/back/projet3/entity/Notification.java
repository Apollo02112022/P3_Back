package com.back.projet3.entity;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String tel;
    private String mail;

 
     @JsonBackReference(value ="userNotification")
    // Données chargées que lorsque l'entité sera utilisée 
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="user_id", referencedColumnName = "id")
     private User user;
     // => on joint la colonne user à notification.
    //  @JsonBackReference(value = "user")
}

 


