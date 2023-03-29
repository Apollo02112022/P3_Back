package com.back.projet3.entity;

import javax.persistence.Entity;
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

    @ManyToMany(mappedBy = "notifications")
    private List<User> user_answers = new ArrayList<>();
     // => On liste les utilisateurs qui répondent à des annonces.

    @OneToMany(mappedBy="notification")
    private List<User>user_notifications;
     // => On liste les utilistateurs qui reçoivent des notifications.

}
