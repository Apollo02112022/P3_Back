package com.back.projet3.entity;

import com.back.projet3.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

// import com.back.projet3.entity.Announcement;

//@Data fait automatiquement les getter et setter via lombok (dépendence).

@Data 
@Entity
@Table(name="user")

// On utilise jsonignore pour ignorer les variables qui créent une boucle infinie.

// @JsonIgnoreProperties({"userAnnouncements", "userAnswers"})
@JsonIgnoreProperties({"userAnnouncements"})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastname;
    private String firstname;

    @NotBlank
    @Size(max=10)
    @Pattern(regexp = "\\w+")
    private String pseudo;

    @NotBlank
    @ValidPassword
    private String password;

    
    // @Lob est une annotation utilisée pour indiquer qu'un champ de l'entité correspondante doit
    //  être stocké sous forme de grand objet binaire (BLOB). Le mot "LOB" signifie "Large Object",
    // il est donc utilisé pour les types de données qui sont trop grands pour être stockés dans 
    // un type de données SQL standard comme VARCHAR ou INTEGER.

    @Lob

    // Stocker et manipuler des images sous forme de tableau de bytes est une pratique courante car 
    // cela permet de stocker efficacement les données binaires d'une image et de les transmettre 
    // facilement via les protocoles de communication tels que HTTP.

    private byte[] picture;
    private String mail;
    private String city;
    private int county;

    private String role;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Announcement> userAnnouncements;

// <----- Relation Many to One concernant les tables user et announcement ----->


// <----- Relation Many to Many concernant les tables user, announcement et notification ----->

    // @ManyToMany
    // @JoinTable(name = "answer",
    //         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "announcement_id", referencedColumnName = "id"))
    // private List<Announcement> answers = new ArrayList<>();
    // // => On liste les annonces qui ont une réponse.

    // @ManyToMany
    // @JoinTable(name = "answer",
    //         joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"))
    // private List<Announcement> notifications = new ArrayList<>();
    // // => On liste les annonces qui ont une notification.

// <---------->

    @OneToMany(mappedBy="user")
    private List<Notification>userNotification;
    // => On liste les notifications reçues par les utilisateurs qui ont créé l'annonce.

}
