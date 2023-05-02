package com.back.projet3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.back.projet3.entity.User;
import com.back.projet3.repository.AnnouncementRepository;
import com.back.projet3.repository.CategoryRepository;
import com.back.projet3.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping("/offer-a-barter")
    public Announcement createAnnouncement(@RequestBody Announcement announcement,
            @RequestParam Long categoryid, @RequestParam Long userid) {

        // récupérer la catégorie correspondant à l'ID
        Category newCategory = categoryRepository.findById(categoryid).get();

        // récupérer l'utilisateur correspondant à l'ID
        User newUser = userRepository.findById(userid).get();

        // définir l'utilisateur et la catégorie pour l'annonce
        announcement.setCategory(newCategory);
        announcement.setUser(newUser);

        return announcementRepository.save(announcement);
    }

    // READ
    @GetMapping("/barters") // api/Announcements GET Liste des annonces
    public List<Announcement> findAllAnnouncement() {

        return announcementRepository.findAll();
    }
    @GetMapping("/barters/{categoryId}")
    public List<Announcement> getAnnouncementsByCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return announcementRepository.findByCategory(category);
    }
    
    @GetMapping("/barters/{id}/view") // api/Announcements GET Liste des annonces
    public Announcement getAnnouncementById(@PathVariable Long id) {
        return announcementRepository.findById(id).get();

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
