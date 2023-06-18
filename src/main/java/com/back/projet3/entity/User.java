package com.back.projet3.entity;

import com.back.projet3.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;


@Data 
@Entity
@Table(name="user")

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

    @Lob
    private byte[] picture;
    private String mail;
    private String city;
    private int county;

    private String role;


    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Announcement> userAnnouncements;

    @OneToMany(mappedBy="user")
    private List<Notification>userNotification;

}
