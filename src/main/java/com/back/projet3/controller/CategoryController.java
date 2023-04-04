package com.back.projet3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.projet3.entity.Category;
import com.back.projet3.repository.CategoryRepository;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    //READ
    @GetMapping("/announcement/{category}") // api/AnnouncreateCategorys GET Liste des utilisateurs
    public List<Category> findAllCategory(){

        return categoryRepository.findAll();
    }

}
