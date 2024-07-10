package com.Mercurius.accountservice.service;

import java.util.List;

import com.Mercurius.accountservice.entity.AccountRepresentation;

public interface IAccountManagementService {
	AccountRepresentation createAccount(AccountRepresentation account);

	AccountRepresentation getAccountById(String accountId);

	boolean updateAccount(AccountRepresentation account);

	boolean deleteAccount(String accountId);

	List<AccountRepresentation> getAllAccounts();

}
