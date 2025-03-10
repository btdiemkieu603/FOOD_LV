package com.kt.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kt.backend.entity.Account;
import com.kt.backend.entity.Order;
import com.kt.backend.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	List<Order> findByAccount(Account account);
	
	@Query(value = "select sum(quantity) from order_food a, item b, product c where a.id = b.order_id and b.product_id = c.id", nativeQuery = true)
	Integer getAllPurchases();
	
	@Query(value = "select sum(quantity) from order_food a, item b, product c where a.id = b.order_id and b.product_id = c.id and product_id = :productId", nativeQuery = true)
	Integer getPurchasesByProduct(@Param("productId") Integer productId);
	
}
