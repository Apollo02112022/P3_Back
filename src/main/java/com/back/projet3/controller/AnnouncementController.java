package com.back.projet3.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
import com.back.projet3.entity.User;
import com.back.projet3.repository.AnnouncementRepository;
import com.back.projet3.repository.UserRepository;
import com.back.projet3.util.ImageUtil;
import com.back.projet3.dto.AnnouncementDto;

//annotation crossorigin pour l'activation de CORS  Cross-origin resource sharing = partage des ressources entre origines multiples »
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private UserRepository userRepository;
   
    // // READ
    // @GetMapping("/offer-a-barter") // api/Announcements GET Liste des annonces
    // public ResponseEntity<?> getAnnouncements() {
    //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }
    
    // CREATE
    @PostMapping("/offer-a-barter")
    public ResponseEntity<?> createAnnouncement(@ModelAttribute AnnouncementDto announcementDto,
            @RequestParam Long userid) {
        // creation d'un nouvel objet announcement()
        Announcement announcement = new Announcement();
        // récupère la description de l'annonce depuis l'objet "AnnouncementDto"
        // et on la définit comme description de l'annonce
        String description = announcementDto.getDescription();
        announcement.setDescription(description);

        // récupère l'objet "User" associé à l'identifiant d'utilisateur fourni
        // et on le définit comme propriétaire de l'annonce
        User userAnnouncement = userRepository.findById(userid).get();
        announcement.setUser(userAnnouncement);
        // initialise une variable pour stocker l'image compressée de l'annonce
        byte[] pictureInByteForm2;

        System.out.println("&&&&&&&&&&&&&&&&&&" + announcementDto.getDescription());
        try {
            // compresse l'image fournie dans l'objet "announcementDto"
            // en utilisant une méthode utilitaire appelée "ImageUtil.compressImage()"
            pictureInByteForm2 = ImageUtil.compressImage(announcementDto.getAnnouncement_picture().getBytes());

            // On définit le tableau d'octets résultant comme valeur du champ
            // "announcement_picture"
            announcement.setAnnouncement_picture(pictureInByteForm2);
        } catch (IOException e) {
            // Si la compression échoue,affichage des erreurs
            e.printStackTrace();
        }

        // enregistre l'objet "Announcement"
        announcementRepository.save(announcement);
        // retourne un objet ResponseEntity avec la nouvelle annonce créée et un code de
        // statut HTTP de CREATED (201)
        return new ResponseEntity<>(announcement, HttpStatus.CREATED);
    }

       // Récupération de l'image d'annonce d'un utilisateur.
    //    @CrossOrigin(origins = "http://localhost:4200")
       @GetMapping("/barters/{id}/image")
       public ResponseEntity<byte[]> getAnnouncementPictureById(@PathVariable Long id) {
           // Recherche de l'annonce correspondant à l'ID fourni dans la base de données.
           Optional<Announcement> annonce = announcementRepository.findById(id);
       
           // Vérification si l'annonce existe.
           if (!annonce.isPresent()) {
               return ResponseEntity.notFound().build();
           }
       
           // Extraction de l'image de l'annonce stockée dans la base de données sous forme compressée.
           byte[] compressedPicture2 = annonce.get().getAnnouncement_picture();
           byte[] decompressedPicture2;
       
           // La méthode decompressImage de la classe ImageUtil est utilisée pour décompresser l'image.
           decompressedPicture2 = ImageUtil.decompressImage(compressedPicture2);
       
           // Création d'un objet HttpHeaders utilisé pour spécifier le type de contenu de la réponse (ici PNG).
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.IMAGE_PNG);
       
           // Création d'un objet ResponseEntity contenant l'image décompressée.
           return new ResponseEntity<>(decompressedPicture2, headers, HttpStatus.OK);
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
    
    @GetMapping("/barters/{id}")
    public AnnouncementDto getAnnouncementById(@PathVariable Long id) {
         Announcement annonce = announcementRepository.findById(id).orElse(null);
         AnnouncementDto annonceToSend = new AnnouncementDto();
         annonceToSend.setUser(annonce.getUser());
         annonceToSend.setDescription(annonce.getDescription());
         annonceToSend.setId(annonce.getId());
        return annonceToSend;
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
    @DeleteMapping("/users/{userid}/barters/{annoucementid}") // /users/{userid}/barters/{annoucementid} DELETE supprime
                                                              // une annonce
    public ResponseEntity<?> deleteUserAnnouncement(@PathVariable Long userid, @PathVariable Long annoucementid) {

        Optional<User> optionalUser = userRepository.findById(userid);
        Optional<Announcement> optinalAnnouncement = announcementRepository.findById(annoucementid);
        List<Announcement> optionalUserAnnouncementList = optionalUser.get().getUserAnnouncements();
        List<Announcement> optionalAnswersList = optionalUser.get().getAnswers();
        List<Announcement> optionalNotificationsList = optionalUser.get().getNotifications();

        if (optionalUser.isPresent() && optinalAnnouncement.isPresent()) {

            for (Announcement userAnnoncementToDelete : optionalUserAnnouncementList) {
                if (Objects.equals(userAnnoncementToDelete, optinalAnnouncement.get())) {

                    Announcement announcementToDelete = optinalAnnouncement.get();

                    optionalUserAnnouncementList.remove(userAnnoncementToDelete);
                    optionalAnswersList.remove(userAnnoncementToDelete);
                    optionalNotificationsList.remove(userAnnoncementToDelete);

                    announcementToDelete.setUser(null);

                    announcementRepository.delete(announcementToDelete);

                    return new ResponseEntity<String>("Your announcement has deleted", HttpStatus.ACCEPTED);
                }
            }
        }

        return new ResponseEntity<String>("Try again", HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/barters/{id}") // api/users/:usersId DELETE supprime une annonce
    public boolean deleteAnnouncement(@PathVariable Long id) {
        announcementRepository.deleteById(id);
        return true;

    }

}
