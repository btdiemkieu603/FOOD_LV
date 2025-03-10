package com.kt.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Cart;
import com.kt.backend.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	List<Item> findByCart(Cart cart);
	
	@Query(value = "select * from item where item.cart_id = :cartId and item.order_id IS NULL", nativeQuery = true)
	List<Item> findItemsCurrentByCart(@Param("cartId") Integer cartId);
	
	@Query(value = "select product_id from item where order_id is not null", nativeQuery = true)
	List<Integer> getAllIdOfProductOrder();
	
}
