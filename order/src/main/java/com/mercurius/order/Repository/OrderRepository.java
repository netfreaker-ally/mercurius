package com.mercurius.order.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercurius.order.entity.Order;



@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	

}
