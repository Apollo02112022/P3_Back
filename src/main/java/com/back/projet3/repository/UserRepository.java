package com.back.projet3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByMail(String username);
  //Boolean existsByEmail(String email);
  User findUserById(Long id);
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}

