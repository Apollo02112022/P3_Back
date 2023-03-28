package com.back.projet3.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;



@Data 
@Entity //Table Announcement
@Table(name="announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String announcement_picture;
    private String description;
   
    @CreationTimestamp
    private Timestamp create_date;  
    
    @ManyToOne
    @JoinColumn(name="user_id") 
    private User user;

    @ManyToMany(mappedBy = "announcement")
    private List<User> users = new ArrayList<>();

}
