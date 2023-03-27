package com.back.projet3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.back.projet3.entity.Announcement;
import com.back.projet3.repository.AnnouncementRepository;

@RestController
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;
//CREATE
    @PostMapping("/offer-a-barter") 
public Announcement createAnnouncement(@RequestBody Announcement announcement){
    // @RequestBody sert a récuperer les information de AnnouncreateAnnouncement
    return announcementRepository.save(announcement);
}
//READ
@GetMapping("/barters") // api/AnnouncreateAnnouncements GET Liste des utilisateurs
public List<Announcement> findAllAnnouncreateAnnouncement(){

    return announcementRepository.findAll();
}
//UPDATE
@PutMapping("/barters/{id}") // api/AnnouncreateAnnouncements/:AnnouncreateAnnouncementsId PUT Mettre à jours un utilisateur
public Announcement UpdateAnnouncreateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement){

    Announcement announcementToUpdate = announcementRepository.findById(id).get();
    announcementToUpdate.setAnnouncement_picture(announcement.getAnnouncement_picture());
    announcementToUpdate.setDescription(announcement.getDescription());
    return announcementRepository.save(announcementToUpdate);
}
//DELETE
@DeleteMapping("/barters/{id}") //api/users/:usersId DELETE supprime un utilisateur
public boolean deleteUser(@PathVariable Long id){
   announcementRepository.deleteById(id);
   return true;
}

}
