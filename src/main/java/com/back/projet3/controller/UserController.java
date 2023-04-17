package com.back.projet3.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.back.projet3.Dto.PasswordDto;
import com.back.projet3.Dto.MailDto;
import com.back.projet3.entity.User;
import com.back.projet3.repository.UserRepository;
import com.back.projet3.security.JwtGenerator;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import org.springframework.http.HttpHeaders;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
=======
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup") // api/signup POST Permet l’inscription
    public User createUser(@RequestBody User user) {
        // @RequestBody sert a récuperer les information de user
        return userRepository.save(user);
    }

    // A modifier pour faire une connection sécurisé
    @PostMapping("/login") // api/login POST Permet la connexion
    public User loginUser(@RequestBody User user) {

        return user;
    }

    // A modifier pour faire une connection sécurisé
    @PostMapping("/logout") // api/logout POST Permet la déconnexion
    public User logoutUser(User user) {

        return user;
    }

    @GetMapping("/users") // api/users GET Liste des utilisateurs
    public List<User> findAllUser() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}/profil") // api/users/{id}/profil GET Détails d’un utilisateur
    public User findUser(@PathVariable Long id) {

        return userRepository.findById(id).get();
    }

    // verification du mdp de l'utilisateur, si il est correct return true sinon false
    @PostMapping("/users/{userid}/profil/check-password")
    public boolean checkUserPassword(@PathVariable Long userid, @RequestBody PasswordDto passwordDto) {
        // Recherche de l'utilisateur dans la base de données
        Optional<User> optionalUser = userRepository.findById(userid);
        // récuperation du mdp transmis.
        String thisPassword = passwordDto.getPassword();
        // récuperation du mdp dans la bdd.
        String userPasswordEncode = optionalUser.get().getPassword();
        // verification que les mdp correspondes
        return passwordEncoder.matches(thisPassword, userPasswordEncode);
    }

    // users/userid/profil/update-password PUT Mettre à jours un mdp utilisateur
    @PutMapping("/users/{userid}/profil/update-password") 
    public boolean updateUserPassword(@PathVariable Long userid, @RequestBody PasswordDto passwordDto) {
        // Recherche de l'utilisateur dans la base de données.
        User userToUpdate = userRepository.findById(userid).get();
        // récuperation et encodage du mdp transmis.
        String newPassword = passwordEncoder.encode(passwordDto.getPassword());
        // mise à jour du mot de passe utilisateur.
        userToUpdate.setPassword(newPassword);
        // insertion du mdp dans la bdd.
        userRepository.save(userToUpdate);
        // renvoie true quand le mdp est changer pour avertir que le changement est
        // effectuer
        return true;
    }

    @PostMapping("/users/{userid}/profil/check-mail")
    public boolean checkUserPassword(@PathVariable Long userid, @RequestBody MailDto mailDto) {
        // chercher si le mail existe déjà dans la bdd
        return userRepository.existsByMail(mailDto.getMail());
    }

    // users/userid/profil/update-password PUT Mettre à jours un mail utilisateur
    @PutMapping("/users/{userid}/profil/update-mail")
    public User UpdateUserMail(@PathVariable Long userid, @RequestBody MailDto mailDto) {
        // Recherche de l'utilisateur dans la base de données.
        User userToUpdate = userRepository.findById(userid).get();
        // récuperation de mail transmis par RequestBody.
        String newMail = mailDto.getMail();
        // mise à jours du mail de l'utilisateur.
        userToUpdate.setMail(newMail);
        //sauvegarde le nouveau mail dans la bdd  et
        // renvoie l'utilisateur avec le mail modifier
        return userRepository.save(userToUpdate);
    }

    // api/users/{userid}/profil DELETE supprime un utilisateur
    @DeleteMapping("/users/{userid}/profil")
    public boolean deleteUser(@PathVariable Long userid) {
        userRepository.deleteById(userid);
        return true;
    }

}
