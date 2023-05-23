package com.back.projet3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.Announcement;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{

    

    Optional<Announcement> findById(Long id);


}
