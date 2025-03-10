package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
