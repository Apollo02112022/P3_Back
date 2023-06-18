package com.back.projet3.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;


@Data
@Entity
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

}
