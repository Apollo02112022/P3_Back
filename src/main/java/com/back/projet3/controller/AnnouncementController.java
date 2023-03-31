package com.back.projet3.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.projet3.entity.Announcement;
import com.back.projet3.entity.Category;
import com.back.projet3.repository.AnnouncementRepository;
import com.back.projet3.repository.CategoryRepository;

@RestController
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    
    // CREATE
    @PostMapping("/offer-a-barter")
    public Announcement createAnnouncement(@RequestBody Announcement announcement,@RequestParam Long categoryid,@RequestParam Long user) {
        // @RequestBody sert a récuperer les information de announcement
        Announcement newAnnouncement = new Announcement();
        Optional<Category> category = categoryRepository.findById(categoryid);
        if (category.isPresent()){
            System.out.println("coucou"+category.get().getGenre());
            Category newCategory = category.get();
            newAnnouncement.setCategory(newCategory);
            newAnnouncement.setDescription(announcement.getDescription());
        }
        // Optional<Announcement> announcementPost = announcementRepository
        // announcement.setCategory(category.get());
        System.out.println("@@@"+categoryid);
        return announcementRepository.save(newAnnouncement);
    }

    // READ
    @GetMapping("/barters") // api/Announcements GET Liste des annonces
    public List<Announcement> findAllAnnouncement() {

        return announcementRepository.findAll();
    }

    // UPDATE
    @PutMapping("/barters/{id}") // api/Announcements/:AnnouncementsId PUT Mettre à jours une annonce
    public Announcement UpdateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {

        Announcement announcementToUpdate = announcementRepository.findById(id).get();
        announcementToUpdate.setAnnouncement_picture(announcement.getAnnouncement_picture());
        announcementToUpdate.setDescription(announcement.getDescription());
        return announcementRepository.save(announcementToUpdate);
    }

    // DELETE
    @DeleteMapping("/barters/{id}") // api/users/:usersId DELETE supprime une annonce
    public boolean deleteUser(@PathVariable Long id) {
        announcementRepository.deleteById(id);
        return true;
    }

}
