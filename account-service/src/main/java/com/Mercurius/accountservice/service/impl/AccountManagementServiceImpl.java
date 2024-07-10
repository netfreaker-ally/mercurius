//package com.Mercurius.accountservice.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.Mercurius.accountservice.entity.AccountRepresentation;
//import com.Mercurius.accountservice.exception.CustomerAlreadyExistsException;
//import com.Mercurius.accountservice.exception.ResourceNotFoundException;
//import com.Mercurius.accountservice.repository.AccountsRepository;
//import com.Mercurius.accountservice.service.IAccountManagementService;
//
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//@Slf4j
//public class AccountManagementServiceImpl implements IAccountManagementService {
//	@Autowired
//	private AccountsRepository accountRepository;
//
//	
//	Date utilDate=new Date();
//	@Override
//	public Mono<AccountRepresentation> createAccount(AccountRepresentation account) {
//	    return accountRepository.findByAccountId(account.getAccountId())
//	        .flatMap(existingAccount -> {
//	            // If the account already exists, throw an exception
//	            return Mono.error(new CustomerAlreadyExistsException(
//	                "Customer already registered with given accountId " + account.getAccountId()));
//	        })
//	        .switchIfEmpty(Mono.defer(() -> {
//	            // If the account does not exist, save the new account
//	            account.setCreatedDate(new java.sql.Date(utilDate.getTime()));
//	           AccountRepresentation accountSaved= accountRepository.save(account);
//	            return ;
//	        }));
//	}
//
//
//
//	@Override
//	public Mono<AccountRepresentation> getAccountById(String accountId) {
//		AccountRepresentation customer = accountRepository.findByAccountId(accountId)
//				.orElseThrow(() -> new ResourceNotFoundException("Account", accountId, ""));
//		return Mono.just(customer);
//	}
//
//	@Override
//	public boolean updateAccount(AccountRepresentation accountDetails) {
//		boolean isUpdated=false;
//		try {
//			AccountRepresentation account = accountRepository.findByAccountId(accountDetails.getAccountId()).orElseThrow(
//					() -> new ResourceNotFoundException("Account not found with id ", accountDetails.getAccountId(), ""));
//
//			account.setAccountId(accountDetails.getAccountId());
//			account.setAccountType(accountDetails.getAccountType());
//			account.setBalance(accountDetails.getBalance());
//			account.setUpdatedDate(new java.sql.Date(utilDate.getTime()));
//			account.setActive(accountDetails.isActive());
////			account.setOffers(accountDetails.getOffers());
//
//			 accountRepository.save(account);
//			 isUpdated=true;
//			 return isUpdated;
//		} catch (Exception e) {
//			throw new RuntimeException("Error Occurred while Updating");
//		}
//
//	}
//
//	@Override
//	public boolean deleteAccount(String accountId) {
//		boolean isDelated=false;
//		try {
//			AccountRepresentation account = accountRepository.findByAccountId(accountId)
//					.orElseThrow(() -> new ResourceNotFoundException("Account not found with id ", accountId, ""));
//
//			accountRepository.delete(account);
//			isDelated=true;
//			return isDelated;
//
//		} catch (Exception e) {
//			throw new RuntimeException("AccountId:"+ accountId+"\nerror Message:\n"+e.getMessage());
//		}
//
//	}
//
//	@Override
//	public Flux<AccountRepresentation> getAllAccounts() {
//		// TODO Auto-generated method stub
//		List<AccountRepresentation> allCustomers = accountRepository.findAll();
//		return allCustomers;
//	}
//
//}
