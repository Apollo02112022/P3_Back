package com.back.projet3.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;

import com.back.projet3.entity.Announcement;

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
    private List<Announcement> announcements;

    @ManyToMany
    @JoinTable(name = "favorite",
        joinColumns = @JoinColumn(name = "user_id"),
        inversionJoinColumns = @JoinColumn(name = "announcement_id"))
    private List<Announcement> announcements = new ArrayList<>();

}
