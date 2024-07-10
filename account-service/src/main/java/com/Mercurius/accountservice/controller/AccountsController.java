//package com.Mercurius.accountservice.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Mercurius.accountservice.constant.AccountConstants;
//import com.Mercurius.accountservice.dto.ResponseDto;
//import com.Mercurius.accountservice.entity.AccountRepresentation;
//import com.Mercurius.accountservice.service.IAccountManagementService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api")
//public class AccountsController {
//	@Autowired
//	private IAccountManagementService accountManagementService;
//	
//	public AccountsController(IAccountManagementService accountManagementService) {
//		super();
//		this.accountManagementService = accountManagementService;
//	}
//
//	@PostMapping("/create")
//	public ResponseEntity<ResponseDto> createAccount(@Valid@RequestBody AccountRepresentation account) {
//		System.out.println(account.toString());
//		accountManagementService.createAccount(account);
//		return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
//	
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<AccountRepresentation> getAccountById(@PathVariable("id") String accountId) {
//		return ResponseEntity.status(HttpStatus.OK).body(accountManagementService.getAccountById(accountId));
//	
//	}
//
//	@PutMapping
//	public ResponseEntity<ResponseDto> updateAccount(
//			@Valid@RequestBody AccountRepresentation account) {
//		boolean isUpdated= accountManagementService.updateAccount(account);
//		  if(isUpdated) {
//	            return ResponseEntity
//	                    .status(HttpStatus.OK)
//	                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
//	        }else{
//	            return ResponseEntity
//	                    .status(HttpStatus.EXPECTATION_FAILED)
//	                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
//	        }
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<ResponseDto> deleteAccount(@PathVariable("id") String accountId) {
//		boolean isDeleted=accountManagementService.deleteAccount(accountId);
//		 if(isDeleted) {
//	            return ResponseEntity
//	                    .status(HttpStatus.OK)
//	                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
//	        }else{
//	            return ResponseEntity
//	                    .status(HttpStatus.EXPECTATION_FAILED)
//	                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
//	        }
//	}
//
//	@GetMapping("/all-users")
//	public ResponseEntity<List<AccountRepresentation>> getAllAccounts() {
//		return ResponseEntity.ok(accountManagementService.getAllAccounts());
//	}
//
//}
