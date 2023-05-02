package com.back.projet3.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.back.projet3.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}
