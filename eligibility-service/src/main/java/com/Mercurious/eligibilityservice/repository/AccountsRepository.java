package com.Mercurious.eligibilityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.Mercurious.eligibilityservice.entity.AccountRepresentation;

import jakarta.transaction.Transactional;
@Repository
public interface AccountsRepository extends JpaRepository<AccountRepresentation, Long> {
	Optional<AccountRepresentation> findByAccountId(String accountId);

	@Transactional
	@Modifying
	void deleteByAccountId(String accountId);
}
