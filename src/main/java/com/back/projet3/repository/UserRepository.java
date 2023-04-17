package com.back.projet3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Object findByMail(String mail);

    Object findByUsername(String username);
    
}
