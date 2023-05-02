package com.back.projet3.repository;


// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMail(String mail);
//     @Query(value = "SELECT * FROM user", nativeQuery = true)
//     public List<User> findAllUsers();
// 
}
