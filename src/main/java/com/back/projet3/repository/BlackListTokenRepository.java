package com.back.projet3.repository;

import org.springframework.stereotype.Repository;
import com.back.projet3.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListTokenEntity, Long>{
  Boolean existsByToken(String token);
}
