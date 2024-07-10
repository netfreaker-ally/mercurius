package com.mercurius.order.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercurius.order.entity.Order;




@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	public List<Order> findByAccountId(String accountId);
	
	

}
