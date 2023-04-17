package com.back.projet3.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.back.projet3.entity.User;
import com.back.projet3.repository.UserRepository;
import com.back.projet3.security.JwtGenerator;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import org.springframework.http.HttpHeaders;


@RestController
public class UserController {

@Autowired
private UserRepository userRepository;

@Autowired
private JwtGenerator tokenGenerator;
   
@PostMapping("/signup") //api/signup POST Permet l’inscription
public User createUser(@RequestBody User user){
    // @RequestBody sert a récuperer les information de user
    return userRepository.save(user);
}

//A modifier pour faire une connection sécurisé
@PostMapping("/login") // api/login POST Permet la connexion
public ResponseEntity<?> loginUser(User user){
    HashMap<String, String> map = new HashMap<String, String>();
    String token = tokenGenerator.generateToken(user.getUsername());
    map.put("user", "sam");
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);
    return ResponseEntity.ok().headers(headers).body(map);


    // map.put("token", token);
    // map.put("user", "sam");
    // return new ResponseEntity<>(map, HttpStatus.OK);
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



