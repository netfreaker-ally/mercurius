package com.Mercurius.accountservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercurius.accountservice.constant.AccountConstants;
import com.Mercurius.accountservice.entity.AccountRepresentation;
import com.Mercurius.accountservice.exception.CustomerAlreadyExistsException;
import com.Mercurius.accountservice.exception.ResourceNotFoundException;
import com.Mercurius.accountservice.repository.AccountsRepository;
import com.Mercurius.accountservice.service.IAccountManagementService;

@Service
public class AccountManagementServiceImpl implements IAccountManagementService {
	@Autowired
	private AccountsRepository accountRepository;

	public AccountManagementServiceImpl(AccountsRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}
	Date utilDate=new Date();
	@Override
	public AccountRepresentation createAccount(AccountRepresentation account) {
		Optional<AccountRepresentation> optionalAccount = accountRepository.findByAccountId(account.getAccountId());
		if (optionalAccount.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given accountId " + account.getAccountId());
		}
		
		account.setCreatedDate(new java.sql.Date(utilDate.getTime()));
		return accountRepository.save(account);
	}

	@Override
	public AccountRepresentation getAccountById(String accountId) {
		AccountRepresentation customer = accountRepository.findByAccountId(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", accountId, ""));
		return customer;
	}

	@Override
	public boolean updateAccount(AccountRepresentation accountDetails) {
		boolean isUpdated=false;
		try {
			AccountRepresentation account = accountRepository.findByAccountId(accountDetails.getAccountId()).orElseThrow(
					() -> new ResourceNotFoundException("Account not found with id ", accountDetails.getAccountId(), ""));

			account.setAccountId(accountDetails.getAccountId());
			account.setAccountType(accountDetails.getAccountType());
			account.setBalance(accountDetails.getBalance());
			account.setUpdatedDate(new java.sql.Date(utilDate.getTime()));
			account.setActive(accountDetails.isActive());
			account.setOffers(accountDetails.getOffers());

			 accountRepository.save(account);
			 isUpdated=true;
			 return isUpdated;
		} catch (Exception e) {
			throw new RuntimeException("Error Occurred while Updating");
		}

	}

	@Override
	public boolean deleteAccount(String accountId) {
		boolean isDelated=false;
		try {
			AccountRepresentation account = accountRepository.findByAccountId(accountId)
					.orElseThrow(() -> new ResourceNotFoundException("Account not found with id ", accountId, ""));

			accountRepository.delete(account);
			isDelated=true;
			return isDelated;

		} catch (Exception e) {
			throw new RuntimeException("AccountId:"+ accountId+"\nerror Message:\n"+e.getMessage());
		}

	}

	@Override
	public List<AccountRepresentation> getAllAccounts() {
		// TODO Auto-generated method stub
		List<AccountRepresentation> allCustomers = accountRepository.findAll();
		return allCustomers;
	}

}
