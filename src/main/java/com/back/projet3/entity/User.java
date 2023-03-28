package com.back.projet3.entity;

import javax.persistence.*;

import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name="announcement_id") 
    private Announcement announcement;

}
