package com.back.projet3.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.back.projet3.validator.ValidName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

// import com.back.projet3.entity.Announcement;

//@Data fait automatiquement les getter et setter via lombok (dépendence).

@Data 
@Entity // Table User
@Table(name="user")

// On utilise jsonignore pour ignorer les variables qui créent une boucle infinie.

@JsonIgnoreProperties({"userAnnouncements", "userFavorites", "userAnswers"})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    
    // @Lob est une annotation utilisée pour indiquer qu'un champ de l'entité correspondante doit
    //  être stocké sous forme de grand objet binaire (BLOB). Le mot "LOB" signifie "Large Object",
    // il est donc utilisé pour les types de données qui sont trop grands pour être stockés dans 
    // un type de données SQL standard comme VARCHAR ou INTEGER.

    @Lob

    // Stocker et manipuler des images sous forme de tableau de bytes est une pratique courante car 
    // cela permet de stocker efficacement les données binaires d'une image et de les transmettre 
    // facilement via les protocoles de communication tels que HTTP.

    private byte[] picture;

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
    private String password;

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

    @ManyToMany
    @JoinTable(name = "answer",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"))
    private List<Announcement> notifications = new ArrayList<>();
    // => On liste les annonces qui ont une notification.

// <---------->

    @OneToMany(mappedBy="notification")
    private List<Notification>user_notification;
    // => On liste les notifications reçues par les utilisateurs qui ont créé l'annonce.

}
