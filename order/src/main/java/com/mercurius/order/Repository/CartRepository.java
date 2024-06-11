package com.mercurius.order.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercurius.order.entity.Cart;
import com.mercurius.order.entity.OrderItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
	List<OrderItem> findAllByOrderItem();
}
