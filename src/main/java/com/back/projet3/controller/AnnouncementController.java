package com.back.projet3.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


//annotation crossorigin pour l'activation de CORS  Cross-origin resource sharing = partage des ressources entre origines multiples »
@CrossOrigin(origins = "http://localhost:4200")
@RestController
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

    @GetMapping("/users/{userid}/barters") // user/:userid/barters GET Liste des annonces
    public List<Announcement> findAnnouncementByUserId(@PathVariable Long userid) {
        Optional<User> optionalUser = userRepository.findById(userid);
         return optionalUser.get().getUserAnnouncements();
    }

    @GetMapping("/barters/category/{categoryId}")
    public List<Announcement> getAnnouncementsByCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return announcementRepository.findByCategory(category);
    }
    
    @GetMapping("/barters/{id}")
    public Announcement getAnnouncementById(@PathVariable Long id) {
        return announcementRepository.findById(id).orElse(null);
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
    @DeleteMapping("/users/{userid}/barters/{annoucementid}") // /users/{userid}/barters/{annoucementid} DELETE supprime une annonce 
    public ResponseEntity<?> deleteUserAnnouncement(@PathVariable Long userid, @PathVariable Long annoucementid) {

        Optional<User> optionalUser = userRepository.findById(userid);
        Optional<Announcement> optinalAnnouncement = announcementRepository.findById(annoucementid);
        List<Announcement> optionalUserAnnouncementList = optionalUser.get().getUserAnnouncements();
        List<Announcement> optionalAnswersList = optionalUser.get().getAnswers();
        List<Announcement> optionalFavoritesList = optionalUser.get().getFavorites();
        List<Announcement> optionalNotificationsList = optionalUser.get().getNotifications();

        if(optionalUser.isPresent() && optinalAnnouncement.isPresent()){

            for(Announcement userAnnoncementToDelete : optionalUserAnnouncementList){
                if(Objects.equals(userAnnoncementToDelete, optinalAnnouncement.get())){
    
                    Announcement announcementToDelete = optinalAnnouncement.get();

                    optionalUserAnnouncementList.remove(userAnnoncementToDelete);
                    optionalAnswersList.remove(userAnnoncementToDelete);
                    optionalFavoritesList.remove(userAnnoncementToDelete);
                    optionalNotificationsList.remove(userAnnoncementToDelete);

                    announcementToDelete.setCategory(null);
                    announcementToDelete.setUser(null);


                    announcementRepository.delete(announcementToDelete);
    
                    return new ResponseEntity<String>("Your announcement has deleted", HttpStatus.ACCEPTED);
                }
            }
        }

        return new ResponseEntity<String>("Try again", HttpStatus.NOT_ACCEPTABLE);
    }
    // notifications answers favorites userAnnouncements

}
