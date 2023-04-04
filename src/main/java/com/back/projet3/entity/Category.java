package com.back.projet3.entity;

import java.util.*;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity //Table Category
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    // on ignore la variable "announcementCategory" pour éviter une boucles infinies
    @JsonIgnore
    private List<Announcement> announcementCategory;








}
