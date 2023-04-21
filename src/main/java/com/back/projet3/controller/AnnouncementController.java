package com.back.projet3.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.projet3.entity.Announcement;
import com.back.projet3.entity.Category;
import com.back.projet3.entity.Announcement;
import com.back.projet3.repository.AnnouncementRepository;
import com.back.projet3.repository.CategoryRepository;
import com.back.projet3.repository.AnnouncementRepository;
import com.back.projet3.util.ImageUtil;
import com.back.projet3.dto.AnnouncementDto;

//annotation crossorigin pour l'activation de CORS  Cross-origin resource sharing = partage des ressources entre origines multiples »
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private CategoryRepository categoryRepository;
   

    
    // CREATE
    @PostMapping("/offer-a-barter")
    public ResponseEntity<?> createAnnouncement(@ModelAttribute AnnouncementDto announcementDto) {
        Announcement announcement = new Announcement();
        String description = announcementDto.getDescription();
        announcement.setDescription(description);
        
        byte[] pictureInByteForm2;

        try {
            pictureInByteForm2 = ImageUtil.compressImage(announcementDto.getAnnouncement_picture().getBytes());
            announcement.setAnnouncement_picture(pictureInByteForm2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        announcementRepository.save(announcement);
        return new ResponseEntity<>(announcement,HttpStatus.CREATED);
    }
       // Récupération de l'image d'un utilisateur.

       @GetMapping("/offer-a-barter/{id}/picture_announcement")
       public ResponseEntity<?> getAnouncementPictureById(@PathVariable Long id) {
   
           // Utilisation du Repository de l'utilisateur pour rechercher l'utilisateur 
           // correspondant à l'ID fourni dans la base de données. Le résultat est 
           // stocké dans un objet Optional, car l'utilisateur peut ne pas exister dans 
           // la BDD.
   
           Optional<Announcement> announcementPicture = announcementRepository.findById(id);
   
           // Extraction de l'utilisateur de l'objet Optional.
   
           Announcement announcement = announcementPicture.get();
   
           // Extraction de l'image de l'utilisateur stockée dans la BDD sous forme
           // compressée.
   
           byte[] compressedPicture2 = announcement.getAnnouncement_picture();
           byte[] decompressedPicture2;
   
           // La méthode decompressImage de la classe ImageUtil est utilisée pour
           // décompresser l'image.
   
           decompressedPicture2 = ImageUtil.decompressImage(compressedPicture2);
   
           // Création d'un objet HttpHeaders utilisé pour spécifier le type de contenu de
           // la réponse (ici PNG).
   
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.IMAGE_PNG);
   
           // Création d'un objet ResponseEntity contenant l'image décompressée.
   
           return ResponseEntity.ok().headers(headers).body(decompressedPicture2);
   
       }


    // @PostMapping("/offer-a-barter")
    // public Announcement createAnnouncement(@RequestBody Announcement announcement,
    //         @RequestParam Long categoryid, @RequestParam Long userid) {

    //     // récupérer la catégorie correspondant à l'ID
    //     Category newCategory = categoryRepository.findById(categoryid).get();

    //     // récupérer l'utilisateur correspondant à l'ID
    //     Announcement newAnnouncement = announcementRepository.findById(userid).get();

    //     // définir l'utilisateur et la catégorie pour l'annonce
    //     announcement.setCategory(newCategory);
    //     announcement.setAnnouncement(newAnnouncement);

    //     return announcementRepository.save(announcement);
    // }

    // READ
    @GetMapping("/barters") // api/Announcements GET Liste des annonces
    public List<Announcement> findAllAnnouncement() {

        return announcementRepository.findAll();
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
    @DeleteMapping("/barters/{id}") // api/users/:usersId DELETE supprime une annonce
    public boolean deleteAnnouncement(@PathVariable Long id) {
        announcementRepository.deleteById(id);
        return true;
    }

}
