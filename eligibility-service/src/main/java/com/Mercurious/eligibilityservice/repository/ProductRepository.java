package com.Mercurious.eligibilityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.Mercurious.eligibilityservice.entity.ProductRepresentation;

import jakarta.transaction.Transactional;
@Repository
public interface ProductRepository extends JpaRepository<ProductRepresentation, Long>{
	Optional<ProductRepresentation> findByProductId(String productId);

	@Transactional
	@Modifying
	void deleteByProductId(String productId);

}
