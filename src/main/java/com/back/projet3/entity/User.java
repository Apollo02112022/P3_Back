package com.back.projet3.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

// import com.back.projet3.entity.Announcement;

//@Data fait automatiquement les getter et setter via lombok (dependence)
@Data 
@Entity //Table User
@Table(name="user")
// on utilise jsonignore pour ignorer les variables qui créent une boucle infinie
@JsonIgnoreProperties({"userAnnouncements", "userFavorites", "userAnswers"})
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

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Announcement> userAnnouncements;

// <----- Relation Many to One concernant les tables user et announcement ----->

    @ManyToMany
    @JoinTable(name = "favorite",
         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
         inverseJoinColumns = @JoinColumn(name = "announcement_id", referencedColumnName = "id"))
    private List<Announcement> favorites = new ArrayList<>();
    // => On liste les annonces qui sont en favori.

// <----- Relation Many to Many concernant les tables user, announcement et notification ----->

    @ManyToMany
    @JoinTable(name = "answer",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "announcement_id", referencedColumnName = "id"))
    private List<Announcement> answers = new ArrayList<>();
    // => On liste les annonces qui ont une réponse.

    // @ManyToMany
    // @JoinTable(name = "answer",
    //         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"))
    // private List<Announcement> notifications = new ArrayList<>();
    // => On liste les annonces qui ont une notification.

// <---------->

//  C'est pour éviter une boucle infinie d'hibernate lorsqu'on récupère une notification
    @JsonManagedReference(value ="user_notification")
    @OneToMany(mappedBy="notification")
    private List<Notification>user_notification;
    // => On liste les notifications reçu par les utilisateurs qui ont créer l'annonce.


}
