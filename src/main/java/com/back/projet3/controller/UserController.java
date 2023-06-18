package com.back.projet3.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.back.projet3.entity.User;
import com.back.projet3.entity.BlackListTokenEntity;
import com.back.projet3.entity.Notification;
import com.back.projet3.repository.BlackListTokenRepository;
import com.back.projet3.repository.NotificationRepository;
import com.back.projet3.repository.UserRepository;
import com.back.projet3.security.JwtGenerator;
import com.back.projet3.security.JwtFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import org.springframework.http.HttpHeaders;

import com.back.projet3.dto.MailDto;
import com.back.projet3.dto.PasswordDto;
import com.back.projet3.dto.UserDto;
import com.back.projet3.util.ImageUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerator tokenGenerator;

    @Autowired
    private JwtFilter tokenFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BlackListTokenRepository blackListTokenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // Création d'un nouvel utilisateur dans BDD via le formulaire d'inscription.

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @ModelAttribute UserDto userDto) {

        User user = new User();


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        String lastname = userDto.getLastname();
        user.setLastname(lastname);
        String firstname = userDto.getFirstname();
        user.setFirstname(firstname);
        String pseudo = userDto.getPseudo();
        user.setPseudo(pseudo);
        String city = userDto.getCity();
        user.setCity(city);
        int county = userDto.getCounty();
        user.setCounty(county);
        String mail = userDto.getMail();
        user.setMail(mail);
        
        user.setRole("USER");


        if (userRepository.findByMail(userDto.getMail()) != null) {
            return new ResponseEntity<>("Un utilisateur avec l'adresse e-mail " + userDto.getMail() + " existe déjà.",
                    HttpStatus.CONFLICT);
        }

        if (userRepository.findByPseudo(userDto.getPseudo()) != null) {
            return new ResponseEntity<>(
                    "Un utilisateur avec le pseudo " + userDto.getPseudo() + " existe déjà.",
                    HttpStatus.CONFLICT);
        }


        byte[] pictureInByteForm;

        try {
            pictureInByteForm = ImageUtil.compressImage(userDto.getPicture().getBytes());
            user.setPicture(pictureInByteForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    // Récupération de l'image d'un utilisateur.

    @GetMapping("/users/{id}/picture")
    public ResponseEntity<byte[]> getUserPictureById(@PathVariable Long id) {

        Optional<User> userPicture = userRepository.findById(id);

        User user = userPicture.get();

        byte[] compressedPicture = user.getPicture();
        byte[] decompressedPicture;

        decompressedPicture = ImageUtil.decompressImage(compressedPicture);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(decompressedPicture, headers, HttpStatus.OK);
    }

  
    @PostMapping("/login") // api/login POST Permet la connexion
    public ResponseEntity<?> loginUser(@RequestBody User userDataFromFront) {
        // Création d'un map
        HashMap<String, String> map = new HashMap<String, String>();
        // Etape 1 Rechercher dans la base de données l'existance de l'utilisateur par
        // son pseudo
        User userInDb = userRepository.findUserByPseudo(userDataFromFront.getPseudo());
        // S'il n'existe pas, renvoyer un bad request
        if (userInDb == null) {
            map.put("message", "L'utilisateur ne correspondent pas");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        // Etape 2 Rechercher dans la base de données la correspondance du mot de
        // passe de l'utilisateur
        String passwordFromFront = userDataFromFront.getPassword();
        System.out.println("@@@@@@@@  Mot de passe :    " + passwordFromFront + userDataFromFront.getPassword());
        // S'ils correspondent, générer un token pour cet utilisateur
        if (passwordEncoder.matches(passwordFromFront, userInDb.getPassword())) { 
            String token = tokenGenerator.generateToken(userDataFromFront.getPseudo(), userInDb.getId(),userInDb.getRole() );
            map.put("token", token);
            map.put("message", "Connexion réussie");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("message", "le mot de passe ne correspond pas");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("custumLogout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        // Obtenir le token depuis la requête http
        System.out.println("@@@@@@@@ LOGOUT !!!!!    " + bearerToken);
        String token = tokenFilter.getTokenString(bearerToken);
        BlackListTokenEntity tokenToBlackList = new BlackListTokenEntity();
        tokenToBlackList.setToken(token);
        blackListTokenRepository.save(tokenToBlackList);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "logout réussie");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/users") // api/users GET Liste des utilisateurs
    public List<User> findAllUser() {

        return userRepository.findAll();
    }


    @GetMapping("/users/{id}/profil") // api/users/{id}/profil GET Détails d’un utilisateur
    public User findUser(@PathVariable Long id) {

        return userRepository.findById(id).get();
    }

    // verification du mdp de l'utilisateur, si il est correct return true sinon
    // false
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
        // sauvegarde le nouveau mail dans la bdd et
        // renvoie l'utilisateur avec le mail modifier
        return userRepository.save(userToUpdate);
    }

    // api/users/{userid}/profil DELETE supprime un utilisateur
    @DeleteMapping("/users/{userid}/profil")
    public ResponseEntity<?> deleteUser(@PathVariable Long userid) {

        Optional<User> optionalUser = userRepository.findById(userid);
        User userToDelete=optionalUser.get();

        // L'user est present dans la bdd ?
        if(optionalUser.isPresent()){
            // récupération de la liste des notif
           List<Notification> listNotification = notificationRepository.findAll();
           
            // pour chaque notif on verifie le user associer 
           for(Notification notification : listNotification){
            // on verifie que l'user est bien le même que celui de la notif
            if(notification.getUser().equals(userToDelete)){
                // on set l'id user à null pour pouvoir supprimer 
                notification.setUser(null);
                notificationRepository.delete(notification);
            }
            
        }
        // on supprime les notifs dans la variable
        userToDelete.getUserNotification().removeAll(listNotification);
        //  et enfin on supprime l'utilisateur
           userRepository.deleteById(userid);
           
           return new ResponseEntity<>("Your account and all data as deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

    }

}
