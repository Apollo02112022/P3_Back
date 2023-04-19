package com.back.projet3.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.back.projet3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByPseudo(String pseudo);
  boolean existsByMail(String mail);
  User findUserByPseudo(String pseudo);
  User findUserByPassword(String password);
  Object findByMail(String mail);
  Object findByPseudo(String pseudo);

}
