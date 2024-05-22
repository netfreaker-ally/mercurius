package com.Mercurious.productservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.Mercurious.productservice.entity.ProductRepresentation;


import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<ProductRepresentation, Long>{
	Optional<ProductRepresentation> findByProductId(String productId);

	@Transactional
	@Modifying
	void deleteByProductId(String productId);

}
