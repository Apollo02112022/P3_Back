package com.back.projet3.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.projet3.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    // public List<Notification> findById(Long id);
   @Query(value = "SELECT * FROM notification WHERE user_id = 1 ", nativeQuery = true)
   List<Notification> findAllNotification();

}
//"@Query" est utilisée pour écrire des requêtes SQL personnalisées dans une méthode d'une interface 
// qui étend une classe de gestion d'entités de JPA, ce qui permet d'interagir avec une base de données 
// en utilisant une syntaxe SQL personnalisée.