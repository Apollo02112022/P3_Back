package com.back.projet3.controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.back.projet3.dto.NotificationDto;
import com.back.projet3.entity.Notification;
import com.back.projet3.entity.User;
import com.back.projet3.repository.NotificationRepository;
import com.back.projet3.repository.UserRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
    public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

//CREATE
    @PostMapping("/proposal_deal") 
    public Notification createNotification(@RequestBody Notification notification){
    // @RequestBody sert a récuperer les information de notification
    return notificationRepository.save(notification);
}
//READ
    @GetMapping("/notifications") // api/Notification GET Liste des notifications
    public List<Notification> findAllNotification(){
    return notificationRepository.findAllNotification();
}
    @GetMapping("/notifications/{userid}")
    public List<Notification> findNotification(@PathVariable Long userid){
      User user = userRepository.findById(userid).get();
      
      return user.getUserNotification();
}
//DELETE
    @DeleteMapping("/notifications/{id}") //api/notifications/{id} DELETE supprime une notification
    public boolean deleteUser(@PathVariable Long id){
    notificationRepository.deleteById(id);
    return true;
}

private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
// variable privée accessible que dans cette classe, final car ne pas être modifié une fois qu'elle a été initialisée.
// objet Map, clé String, SseEmitter, variable userEmitters.
// ConcurrentHashMap est une impléméntation de Map.

@PostMapping("/postMessage")
// Méthode @postMapping qui sera appelé lors d'une requête http post avec url
    public ResponseEntity<?> postMessage(@RequestBody NotificationDto message, @RequestParam Long userAnnounceId) {
    // @requestParam récupère le message à envoyer et l'id qu'il doit recevoir
        SseEmitter emitter = userEmitters.get((userAnnounceId).toString());
        System.out.println("Notif gg");

        // emitter récupère l'id de l'utilisateur (userEmitters)
        if (emitter != null) {
        System.out.println("Notif OK");

        // si l'emitter n'est pas null
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
                // signifie que l'utilisateur est connecté et qu'un émetteur à été créé pour lui => méthode emitter.send
            } catch (IOException error) {
        System.out.println("Notif not");

                // gère les exceptions (erreur)
                userEmitters.remove((userAnnounceId).toString());
                // l'id de l'émetteur retiré de la variable userEmitters
            }
        }

       User userReceiver = userRepository.findById(userAnnounceId).get();

       Notification userNotification = new Notification();

       userNotification.setMail(message.getMail());
       userNotification.setTel(message.getTel());
       userNotification.setMessage(message.getMessage());
       userNotification.setUser(userReceiver);
       Notification notif =  notificationRepository.save(userNotification);
       NotificationDto newNotif = new NotificationDto();
       newNotif.setMessage(notif.getMessage()); 
       notificationRepository.save(userNotification);
        // return newNotif;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "notification created");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

 @GetMapping("/streamMessages")
// requête http get sur l'url
//  @getMapping permet de créer un nouvel émetteur (SseEmitter) et de le stocker dans la variable userEmitters
    public SseEmitter streamMessages(@RequestParam("userId") String userId) {
        // @requestParam récupère l'id de l'utilisateur pour lequel l'émetteur doit être créé
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        // Long.MAX.VALUE signifie que la connexion restera ouverte aussi longtemps que possible pour permettre la 
        // diffusion en continue de données en temps réel. 
        userEmitters.put(userId, emitter);
        // userId est la clé de la paire clé-valeur et emitter la valeur associé à cette clé.
        // Ajoute à Map un objet SseEmitters(emitter) associé à userId

        emitter.onTimeout(() -> userEmitters.remove(userId));
        // Méthode qui permet de définir l'action à effectuer lorsque la connexion avec l'objet SseEmitters expire
        // Dans ce cas, on supprime l'id 
        emitter.onCompletion(() -> userEmitters.remove(userId));
        // Méthode qui permet de définir l'action à effectuer lorsque la connexion avec l'objet SseEmitters est fermée
        // Dans ce cas, on supprime l'id 
  
        return emitter;
    }
 

    // @GetMapping("/users/{userid}/notifications")
    // private List<Notification> notificationOfUserNotification (@PathVariable Long userId) {
    //     userRepository.findById(userId);
    //     Optional<User> userNotif = userRepository.findById(userId);
    //     if (userNotif.isPresent()) {
    //         return userNotif.get().getUserNotification();
    //     } else {
    //         return null;
    //     }         
    // }
    }


