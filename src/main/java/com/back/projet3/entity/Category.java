package com.back.projet3.entity;

import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity //Table Category
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean fruits;
    private boolean vegetables;
    private boolean tin_can_jars;
    private boolean other;
    private boolean batch;

    @OneToMany(mappedBy = "category")
    private List<Announcement> category;








}
