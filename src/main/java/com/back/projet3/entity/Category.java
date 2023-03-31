package com.back.projet3.entity;

import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Enumerated;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity // Table Category
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;

    @OneToMany(mappedBy = "category")
    private List<Announcement> listCategory;

}
