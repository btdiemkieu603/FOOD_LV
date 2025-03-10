package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
