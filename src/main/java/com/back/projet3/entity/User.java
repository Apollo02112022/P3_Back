package com.back.projet3.entity;

import javax.persistence.*;


import lombok.Data;

import java.util.List;
import java.util.ArrayList;

// import com.back.projet3.entity.Announcement;

//@Data fait automatiquement les getter et setter via lombok (dependence)
@Data 
@Entity //Table User
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastname ;
    private String firstname ;
    private String mail ;
    private String password ;
    private String pseudo ;
    private String city ;
    private int county ;
    private String picture ;

    @OneToMany(mappedBy = "user")
    private List<Announcement> userAnnouncements;

// <----- Relation Many to One concernant les tables user et announcement ----->

    @ManyToMany
    @JoinTable(name = "favorite",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "announcement_id"))
    private List<Announcement> favorites = new ArrayList<>();
    // => On liste les annonces qui sont en favori.

// <----- Relation Many to Many concernant les tables user, announcement et notification ----->

    @ManyToMany
    @JoinTable(name = "answer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "announcement_id"))
    private List<Announcement> answers = new ArrayList<>();
    // => On liste les annonces qui ont une réponse.

    @ManyToMany
    @JoinTable(name = "answer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private List<Announcement> notifications = new ArrayList<>();
    // => On liste les annonces qui ont une notification.

// <---------->

    @OneToMany(mappedBy="notification")
    private List<Notification>user_notification;
    // => On liste les notifications reçu par les utilisateurs qui ont créer l'annonce.

 

}
