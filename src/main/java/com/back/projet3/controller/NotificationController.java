package com.back.projet3.controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.back.projet3.dto.NotificationDto;
import com.back.projet3.entity.Notification;
import com.back.projet3.entity.User;
import com.back.projet3.repository.NotificationRepository;
import com.back.projet3.repository.UserRepository;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import org.springframework.http.MediaType;
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
    @GetMapping("/notifications/{id}")
    public Notification findNotification(@PathVariable Long id){
    return notificationRepository.findById(id).get();
}
//DELETE
    @DeleteMapping("/notifications/{id}") //api/users/:usersId DELETE supprime une notification
    public boolean deleteUser(@PathVariable Long id){
    notificationRepository.deleteById(id);
    return true;
}
// quelques exemples :
// private List<String> array = new ArrayList();

// obj: { name: string, age: number} = {
//     name : "Laurie",
//     age : 23,
//   }
//   arr: string[]= [ "Laurie", "Yvens" ]
// string => clé
// name => clé


private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
// variable privée accessible que dans cette classe, final car ne pas être modifié une fois qu'elle a été initialisée.
// objet Map, clé String, SseEmitter, variable userEmitters.
// ConcurrentHashMap est une impléméntation de Map.

@PostMapping("/postMessage")
// Méthode @postMapping qui sera appelé lors d'une requête http post avec url
    public boolean postMessage(@RequestBody NotificationDto message, @RequestParam("userAnnounceId") Long userAnnounceId) {
    // @requestParam récupère le message à envoyer et l'id qu'il doit recevoir
        // SseEmitter emitter = userEmitters.get(userAnnounceId);
        // // emitter récupère l'id de l'utilisateur (userEmitters)
        // if (emitter != null) {
        // // si l'emitter n'est pas null
        //     try {
        //         emitter.send(message, MediaType.APPLICATION_JSON);
        //         // signifie que l'utilisateur est connecté et qu'un émetteur à été créé pour lui => méthode emitter.send
        //     } catch (IOException error) {
        //         // gère les exceptions (erreur)
        //         userEmitters.remove(userAnnounceId);
        //         // l'id de l'émetteur retiré de la variable userEmitters
        //     }
        // }

       User userSender = userRepository.findById(userAnnounceId).get();

       Notification userNotification = new Notification();

       userNotification.setMail(message.getMail());
       userNotification.setTel(message.getTel());
       userNotification.setMessage(message.getMessage());
       userNotification.setUser(userSender);
        
       notificationRepository.save(userNotification);

       return true;
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
 

    // @GetMapping("/")
}

