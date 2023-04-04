package com.back.projet3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.back.projet3.entity.User;
import com.back.projet3.repository.UserRepository;

// import com.back.projet3.security.Config;

@RestController
public class UserController {

@Autowired
private UserRepository userRepository;
    
@PostMapping("/signup") //api/signup POST Permet l’inscription
public User createUser(@RequestBody User user){
    // @RequestBody sert a récuperer les information de user
    return userRepository.save(user);
}

//A modifier pour faire une connection sécurisé
@PostMapping("/login") // api/login POST Permet la connexion
public User loginUser(User user){

    return user;
}

//A modifier pour faire une connection sécurisé
@PostMapping("/logout") // api/logout POST Permet la déconnexion
public User logoutUser(User user){

    return user;
}

@GetMapping("/users") // api/users GET Liste des utilisateurs
public List<User> findAllUser(){

    return userRepository.findAll();
}


@GetMapping("/users/{id}/profil") // api/users/{id}/profil GET Détails d’un utilisateur
public User findUser(@PathVariable Long id){

    return userRepository.findById(id).get();
}


@PutMapping("/users/{id}/profil") // api/users/:usersId PUT Mettre à jours un utilisateur
public User UpdateUser(@PathVariable Long id, @RequestBody User user){

    User userToUpdate = userRepository.findById(id).get();
    userToUpdate.setPassword(user.getPassword());
    userToUpdate.setMail(user.getMail());
    return userRepository.save(userToUpdate);
}

@DeleteMapping("/users/{id}/profil") //api/users/:usersId DELETE supprime un utilisateur
public boolean deleteUser(@PathVariable Long id){
   userRepository.deleteById(id);
   return true;
}

}



