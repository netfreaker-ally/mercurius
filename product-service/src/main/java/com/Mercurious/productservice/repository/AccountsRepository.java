package com.Mercurious.productservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.Mercurious.productservice.entity.AccountRepresentation;

import jakarta.transaction.Transactional;

public interface AccountsRepository extends JpaRepository<AccountRepresentation, Long> {
	Optional<AccountRepresentation> findByAccountId(String accountId);

	@Transactional
	@Modifying
	void deleteByAccountId(String accountId);
}
