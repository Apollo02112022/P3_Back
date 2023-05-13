package com.back.projet3.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

// import com.back.projet3.entity.User;

@Data
@Entity // Table Announcement
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] announcement_picture;

    private String description;

    @CreationTimestamp
    private Timestamp create_date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "answers")
    private List<User> user_answers = new ArrayList<>();
    // => On liste les utilisateurs qui ont créé des réponses.

}
