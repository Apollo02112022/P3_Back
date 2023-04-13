package com.back.projet3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.Announcement;
import com.back.projet3.entity.Category;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{
    List<Announcement> findByCategory(Category category);
    Optional<Announcement> findById(Long id);
}
