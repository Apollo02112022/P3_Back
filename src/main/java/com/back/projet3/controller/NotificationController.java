package com.back.projet3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.back.projet3.entity.Notification;
import com.back.projet3.repository.NotificationRepository;



@RestController
    public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

//CREATE
    @PostMapping("/proposal_deal") 
    public Notification createNotification(@RequestBody Notification notification){
    // @RequestBody sert a récuperer les information de notification
    return notificationRepository.save(notification);
}
//READ
    @GetMapping("/notifications") // api/Notification GET Liste des notifications
    public List<Notification> findAllNotification(){

    return notificationRepository.findAll();
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
}
