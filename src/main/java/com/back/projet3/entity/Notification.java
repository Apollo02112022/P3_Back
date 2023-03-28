package com.back.projet3.entity;

import javax.persistence.Entity;
import javax.persistence.*;


import lombok.Data;

@Data
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
}
