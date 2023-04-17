package com.back.projet3.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.back.projet3.dto.UserDto;
import com.back.projet3.entity.User;
import com.back.projet3.repository.UserRepository;
import com.back.projet3.util.ImageUtil;

@RestController

@RequestMapping("/api")

@CrossOrigin(origins = "http://localhost:4200")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Création d'un nouvel utilisateur dans la base de données via le formulaire
    // d'inscription.

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@ModelAttribute UserDto userDto) {

        User user = new User();

        // System.out.println("@@@@@@@@@@" + userDto.getPassword());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        String lastname = userDto.getLastname();
        user.setLastname(lastname);
        String firstname = userDto.getFirstname();
        user.setFirstname(firstname);
        String username = userDto.getUsername();
        user.setUsername(username);
        String city = userDto.getCity();
        user.setCity(city);
        int county = userDto.getCounty();
        user.setCounty(county);
        String mail = userDto.getMail();
        user.setMail(mail);

        if (userRepository.findByMail(userDto.getMail()) != null) {
            return new ResponseEntity<>("Un utilisateur avec l'adresse e-mail " + userDto.getMail() + " existe déjà.",
                    HttpStatus.CONFLICT);
        }

        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            return new ResponseEntity<>(
                    "Un utilisateur avec le nom d'utilisateur " + userDto.getUsername() + " existe déjà.",
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

    // A modifier pour faire une connection sécurisé
    @PostMapping("/login") // api/login POST Permet la connexion
    public User loginUser(User user) {

        return user;
    }

    // A modifier pour faire une déconnection sécurisé
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

    @PutMapping("/users/{id}/profil") // api/users/:usersId PUT Mettre à jours un utilisateur
    public User UpdateUser(@PathVariable Long id, @RequestBody User user) {

        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setMail(user.getMail());
        return userRepository.save(userToUpdate);
    }

    @DeleteMapping("/users/{id}/profil") // api/users/:usersId DELETE supprime un utilisateur
    public boolean deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return true;
    }

}
