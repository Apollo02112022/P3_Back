package com.back.projet3.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.back.projet3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByUsername(String username);
  boolean existsByMail(String mail);
  User findUserByUsername(String username);
  User findUserByPseudo(String pseudo);
  User findUserByPassword(String password);
  Object findByMail(String mail);
  Object findByUsername(String username);
  Optional<User> findByMail(String mail);
  Optional<User> findByUsername(String username);

}
